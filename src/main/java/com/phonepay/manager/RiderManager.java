package com.phonepay.manager;

import com.phonepay.exception.InvalidRiderIdException;
import com.phonepay.exception.RiderAlreadyExistException;
import com.phonepay.model.Rider;

import java.util.HashMap;
import java.util.Map;

public class RiderManager {
    private Map<String, Rider> riderMap = new HashMap<>();

    public void createRider(Rider newRider){
        if(riderMap.containsKey(newRider.getId())){
            throw new RiderAlreadyExistException();
        }
        riderMap.put(newRider.getId(), newRider);
    }

    public Rider getRider(String id){
        if(riderMap.containsKey(id)){
            return riderMap.get(id);
        } else throw new InvalidRiderIdException();

    }
}
