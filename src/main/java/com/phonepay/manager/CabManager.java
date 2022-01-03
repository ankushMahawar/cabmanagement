package com.phonepay.manager;

import com.phonepay.exception.CabAlreadyExistsException;
import com.phonepay.exception.CabNotFoundException;
import com.phonepay.exception.CabStateAlreadyUpdatedException;
import com.phonepay.exception.NoTripFoundException;
import com.phonepay.model.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CabManager {

    private Map<String, Cab> cabMap = new HashMap<>();
    private Map<String, List<CabStatus>> cabStatusHistoryMap = new HashMap<>();

    public void createCab(Cab newCab) {
        if (cabMap.containsKey(newCab.getId())) {
            throw new CabAlreadyExistsException();
        } else {
            cabMap.put(newCab.getId(), newCab);
        }
    }

    public void onboardCabs(List<CabInputData> cabInputData) {
        for (CabInputData cabdata : cabInputData) {
            if (cabdata.getCabState() == CabState.ON_TRIP) {
                Cab cab = new Cab(cabdata.getCabId(), cabdata.getCabState());
                cab.addTrip(new Trip());
                createCab(cab);
            } else {
                Cab cab = new Cab(cabdata.getCabId(), new Location(cabdata.getCityId()));
                createCab(cab);
            }
        }

    }

    public void updateCabLocation(String cabId, Location newLocation) {
        if (!cabMap.containsKey(cabId)) {
            throw new CabNotFoundException();
        }
        cabMap.get(cabId).updateLocation(newLocation);
    }

    public void updateCabTrip(String cabId, Trip trip) {
        if (!cabMap.containsKey(cabId)) {
            throw new CabNotFoundException();
        }
        Cab cab = cabMap.get(cabId);
        if (cab.getCabState() == CabState.ON_TRIP) {
            throw new CabStateAlreadyUpdatedException();
        }
        CabStatus status = cab.getCurrentStatus();
        status.setEndTime(trip.getStartTime());
        cab.addTrip(trip);
        if (cabStatusHistoryMap.containsKey(cabId)) {
            cabStatusHistoryMap.get(cabId).add(status);
        } else {
            List<CabStatus> statuses = new ArrayList<>();
            statuses.add(status);
            cabStatusHistoryMap.put(cabId, statuses);
        }
    }

    public Trip endCabTrip(String cabId, LocalDateTime tripEndTime) {
        if (!cabMap.containsKey(cabId)) {
            throw new CabNotFoundException();
        }
        Cab cab = cabMap.get(cabId);
        if (cab.getTrip() == null) {
            throw new NoTripFoundException();
        }
        CabStatus status = cab.getCurrentStatus();
        status.setEndTime(tripEndTime);
        if (cabStatusHistoryMap.containsKey(cabId)) {
            cabStatusHistoryMap.get(cabId).add(status);
        } else {
            List<CabStatus> statuses = new ArrayList<>();
            statuses.add(status);
            cabStatusHistoryMap.put(cabId, statuses);
        }
        return  cab.endTrip(tripEndTime);
    }

    public List<Cab> getCabs(Location location) {
        List<Cab> result = new ArrayList<>();
        for (Cab cab : cabMap.values()) {
            if (cab.getCabState() == CabState.IDLE && cab.getLocation().equals(location)) {
                result.add(cab);
            }
        }
        return result;
    }

    public Map<String, List<CabStatus>> getAllCabStatusHistory(){
        return cabStatusHistoryMap;
    }


}
