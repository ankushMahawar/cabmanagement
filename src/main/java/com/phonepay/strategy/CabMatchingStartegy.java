package com.phonepay.strategy;

import com.phonepay.model.Cab;
import com.phonepay.model.Location;

import java.util.List;
import java.util.Optional;

public interface CabMatchingStartegy {
    Optional<Cab> getMatchingCab(List<Cab> cabs, Location from, Location to);
}
