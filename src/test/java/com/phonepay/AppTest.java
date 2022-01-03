/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.phonepay;

import com.phonepay.manager.CabManager;
import com.phonepay.manager.RiderManager;
import com.phonepay.manager.TripManager;
import com.phonepay.model.Trip;
import com.phonepay.model.TripStatus;
import com.phonepay.service.CabBookingAdminService;
import com.phonepay.service.CabBookingService;
import com.phonepay.strategy.CabMatchingStartegy;
import com.phonepay.strategy.DefaultCabMatchingStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppTest {
    CabBookingService cabBookingService;
    CabBookingAdminService cabAdminService;

    @Before
    public void setUp() {
        CabManager cabManager = new CabManager();
        RiderManager riderManager = new RiderManager();
        CabMatchingStartegy cabMatchingStartegy = new DefaultCabMatchingStrategy();
        TripManager tripManager = new TripManager(cabManager, riderManager, cabMatchingStartegy);
        cabBookingService = new CabBookingService(riderManager, tripManager);
        cabAdminService = new CabBookingAdminService(cabManager, tripManager);
    }

    @Test
    public void testSimpleCabBooking() {
        cabAdminService.registerCab("Cab1", "Mumbai");
        cabAdminService.registerCab("Cab2", "Delhi");
        cabBookingService.registerRider("Rider1", "TestRider1");
        cabBookingService.book("Rider1", "Mumbai", "Patna", 100.0);
        List<Trip> trip = cabAdminService.getTripHistory("Rider1");
        assertTrue(trip.size() == 1);
        assertTrue(trip.get(trip.size() - 1).getCab().getId().equals("Cab1"));
        assertTrue(trip.get(trip.size() - 1).getStatus() == TripStatus.IN_PROGRESS);
        cabBookingService.endTrip("Cab1");
        assertTrue(trip.get(trip.size() - 1).getStatus() == TripStatus.COMPLETED);
    }

    @Test
    public void testCabMatchingStartegy() throws InterruptedException {
        cabAdminService.registerCab("Cab2", "Mumbai");
        Thread.sleep(100);
        cabAdminService.registerCab("Cab1", "Mumbai");
        Thread.sleep(100);
        cabAdminService.registerCab("Cab3", "Mumbai");
        cabAdminService.registerCab("Cab4", "Delhi");
        cabBookingService.registerRider("Rider1", "TestRider1");
        cabBookingService.book("Rider1", "Mumbai", "Patna", 100.0);
        List<Trip> trip = cabAdminService.getTripHistory("Rider1");
        assertTrue(trip.size() == 1);
        assertTrue(trip.get(trip.size() - 1).getCab().getId().equals("Cab2"));

    }


    @Test
    public void testCityWithMaxBooking() throws InterruptedException {
        cabAdminService.registerCab("Cab1", "Mumbai");
        cabAdminService.registerCab("Cab2", "Delhi");
        cabAdminService.registerCab("Cab3", "Delhi");
        cabAdminService.registerCab("Cab4", "Jaipur");
        cabBookingService.registerRider("Rider1", "TestRider1");
        cabBookingService.registerRider("Rider2", "TestRider2");
        cabBookingService.registerRider("Rider3", "TestRider3");
        cabBookingService.book("Rider1", "Mumbai", "Patna", 100.0);
        cabBookingService.endTrip("Cab1");
        cabBookingService.book("Rider2", "Delhi", "Chennai", 100.0);
        cabBookingService.book("Rider3", "Mumbai", "Delhi", 100.0);
        List<String> cityWithMostBooking = cabAdminService.getCityWithMostBooking();
        assertEquals(cityWithMostBooking.get(0), "Mumbai");

        cabBookingService.book("Rider1", "Delhi", "Chennai", 100.0);

        cityWithMostBooking = cabAdminService.getCityWithMostBooking();
        assertEquals(cityWithMostBooking.size(), 2);

        cabBookingService.endTrip("Cab2");
        cabBookingService.book("Rider2", "Delhi", "Chennai", 100.0);

        cityWithMostBooking = cabAdminService.getCityWithMostBooking();
        assertEquals(cityWithMostBooking.get(0), "Delhi");
        System.out.println(cabAdminService.getCabStatusHistory());
    }
}