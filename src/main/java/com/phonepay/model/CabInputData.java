package com.phonepay.model;

public class CabInputData {
    private String cabId;
    private String cityId;
    private CabState cabState;

    public CabInputData(String cabId, String cityId, CabState cabState) {
        this.cabId = cabId;
        this.cityId = cityId;
        this.cabState = cabState;
    }

    public String getCabId() {
        return cabId;
    }

    public String getCityId() {
        return cityId;
    }

    public CabState getCabState() {
        return cabState;
    }
}
