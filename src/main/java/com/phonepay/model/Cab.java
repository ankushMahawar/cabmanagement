package com.phonepay.model;

import java.time.LocalDateTime;

public class Cab {
    private String id;
    private Location location;
    private CabStatus currentStatus;
    private Trip trip;

    public Cab(String id, CabState cabState) {
        this.id = id;
        this.updateStatus(cabState);
    }

    public Cab(String id, Location location) {
        this.id = id;
        this.location = location;
        this.updateStatus(CabState.IDLE);
    }

    public String getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public CabStatus getCurrentStatus() {
        return currentStatus;
    }

    public CabState getCabState() {
        return currentStatus.getCabState();
    }

    public Trip getTrip(){ return trip;}

    public void updateLocation(Location location) {
        this.location = location;
    }

    public void updateStatus(CabState cabState) {
        CabStatus newStatus = new CabStatus();
        newStatus.setCabState(cabState);
        newStatus.setStartTime(LocalDateTime.now());
        this.currentStatus = newStatus;
    }

    public void addTrip(Trip trip) {
        CabStatus newStatus = new CabStatus();
        newStatus.setCabState(CabState.ON_TRIP);
        newStatus.setStartTime(trip.getStartTime());
        this.currentStatus = newStatus;
        this.trip = trip;
    }

    public Trip endTrip(LocalDateTime endTripTime) {
        CabStatus newStatus = new CabStatus();
        newStatus.setCabState(CabState.IDLE);
        newStatus.setStartTime(endTripTime);
        this.currentStatus = newStatus;
        Trip prevTrip = trip;
        this.trip = null;
        return prevTrip;
    }
}
