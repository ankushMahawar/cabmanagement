package com.phonepay.model;

import java.time.LocalDateTime;

public class Trip {
    private Cab cab;
    private Rider rider;
    private Location fromLocation;
    private Location toLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;
    private TripStatus status;

    //Dummy Trip as per requirements. This is not required but had to implement as requested in requirements.
    public Trip() {

    }

    public Trip(Cab cab, Rider rider, Location fromLocation, Location toLocation, LocalDateTime startTime, Double price) {
        this.cab = cab;
        this.rider = rider;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.startTime = startTime;
        this.price = price;
        this.status = TripStatus.IN_PROGRESS;
    }

    public Cab getCab() {
        return cab;
    }

    public Rider getRider() {
        return rider;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Double getPrice() {
        return price;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void endTrip(LocalDateTime endTime){
        this.status = TripStatus.COMPLETED;
        this.endTime = endTime;
    }
}
