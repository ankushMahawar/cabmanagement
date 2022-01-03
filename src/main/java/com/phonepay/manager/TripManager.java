package com.phonepay.manager;

import com.phonepay.exception.NoCabAvailableException;
import com.phonepay.model.*;
import com.phonepay.strategy.CabMatchingStartegy;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TripManager {
    private Map<String, List<Trip>> riderTripMap = new HashMap<>();
    private CabManager cabManager;
    private RiderManager riderManager;
    private CabMatchingStartegy cabMatchingStartegy;

    public TripManager(CabManager cabManager, RiderManager riderManager, CabMatchingStartegy cabMatchingStartegy) {
        this.cabManager = cabManager;
        this.riderManager = riderManager;
        this.cabMatchingStartegy = cabMatchingStartegy;
    }

    public void createTrip(Rider rider, Location from, Location to, Double price, LocalDateTime tripStartTime) {
        List<Cab> nearByCabs = cabManager.getCabs(from);
        Optional<Cab> selectedCab = cabMatchingStartegy.getMatchingCab(nearByCabs, from, to);
        if (!selectedCab.isPresent()) {
            throw new NoCabAvailableException();
        }
        Trip trip = new Trip(selectedCab.get(), rider, from, to, tripStartTime, price);
        cabManager.updateCabTrip(selectedCab.get().getId(), trip);
        if (riderTripMap.containsKey(rider.getId())) {
            riderTripMap.get(rider.getId()).add(trip);
        } else {
            List<Trip> trips = new ArrayList<>();
            trips.add(trip);
            riderTripMap.put(rider.getId(), trips);
        }
    }

    public void endTrip(String cabId) {
        LocalDateTime tripEndTime = LocalDateTime.now();
        Trip endedTrip = cabManager.endCabTrip(cabId, tripEndTime);
        endedTrip.endTrip(tripEndTime);
    }

    public List<Trip> getTripHistory(String riderId){
        return riderTripMap.get(riderId);
    }

    public List<Trip> getAllTrips(){
        return riderTripMap.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}
