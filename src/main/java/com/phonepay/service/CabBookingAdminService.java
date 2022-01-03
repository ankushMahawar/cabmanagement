package com.phonepay.service;

import com.phonepay.manager.CabManager;
import com.phonepay.manager.TripManager;
import com.phonepay.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class CabBookingAdminService {
    private CabManager cabManager;
    private TripManager tripManager;

    public CabBookingAdminService(CabManager cabManager, TripManager tripManager) {
        this.cabManager = cabManager;
        this.tripManager = tripManager;
    }

    // "register/cabs/" + Http Post
    public void registerCabs(List<CabInputData> cabInputData) {
        cabManager.onboardCabs(cabInputData);
    }

    //"register/cab" + Http post
    public void registerCab(String cabId, String cityId) {
        cabManager.createCab(new Cab(cabId, new Location(cityId)));
    }

    //"cab/updateLocation" + POST
    public void updateCabLocation(String cabId, String newCityId) {
        cabManager.updateCabLocation(cabId, new Location(newCityId));
    }

    public List<Trip> getTripHistory(String riderId) {
        return tripManager.getTripHistory(riderId);
    }

    public List<String> getCityWithMostBooking(){
        List<Trip> trips = tripManager.getAllTrips();
        Map<Location, List<Trip>> tripsByLocation = trips.stream()
                .collect(groupingBy(Trip::getFromLocation));
        int maxSize = 0;
        List<String > maxTripLocation = new ArrayList<>();
        for(Map.Entry<Location, List<Trip>> entry : tripsByLocation.entrySet()){
            if(entry.getValue().size() >= maxSize){
                if(entry.getValue().size() == maxSize){
                    maxTripLocation.add(entry.getKey().getCityId());
                } else {
                    maxSize = entry.getValue().size();
                    maxTripLocation = new ArrayList<>();
                    maxTripLocation.add(entry.getKey().getCityId());
                }
            }
        }
        return maxTripLocation;
    }

    public Map<String, List<CabStatus>> getCabStatusHistory(){
        return cabManager.getAllCabStatusHistory();
    }
}
