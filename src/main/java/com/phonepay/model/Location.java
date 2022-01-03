package com.phonepay.model;

import java.util.Objects;

public class Location {

    private String cityId;

    public Location(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(cityId, location.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId);
    }
}
