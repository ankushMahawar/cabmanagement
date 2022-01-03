package com.phonepay.strategy;

import com.phonepay.model.Cab;
import com.phonepay.model.Location;
import com.phonepay.strategy.CabMatchingStartegy;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DefaultCabMatchingStrategy implements CabMatchingStartegy {
    @Override
    public Optional<Cab> getMatchingCab(List<Cab> cabs, Location from, Location to) {
        List<Cab> sortedByWeightTime = cabs.stream()
                .sorted(Comparator.comparing((Cab cab) -> cab.getCurrentStatus().getStartTime()))
                .collect(Collectors.toList());
        LocalDateTime startTime = sortedByWeightTime.get(0).getCurrentStatus().getStartTime();
        return sortedByWeightTime.stream().filter(cab -> (cab.getCurrentStatus().getStartTime().equals(startTime))).findAny();

    }
}
