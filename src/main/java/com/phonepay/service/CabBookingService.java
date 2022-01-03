package com.phonepay.service;

import com.phonepay.manager.RiderManager;
import com.phonepay.manager.TripManager;
import com.phonepay.model.Location;
import com.phonepay.model.Rider;

import java.time.LocalDateTime;

public class CabBookingService {
    private RiderManager riderManager;
    private TripManager tripManager;

    public CabBookingService(RiderManager riderManager, TripManager tripManager) {
        this.riderManager = riderManager;
        this.tripManager = tripManager;
    }

    public void registerRider(String riderId, String riderName) {
        riderManager.createRider(new Rider(riderId, riderName));
    }

    public void book(String riderId, String fromCityId, String toCityId, Double price) {
        tripManager.createTrip(riderManager.getRider(riderId),
                new Location(fromCityId),
                new Location(toCityId),
                price,
                LocalDateTime.now());
    }

    public void endTrip(String cabId) {
        tripManager.endTrip(cabId);
    }
}
