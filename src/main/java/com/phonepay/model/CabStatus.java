package com.phonepay.model;

import java.time.LocalDateTime;

public class CabStatus {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CabState cabState;

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public CabState getCabState() {
        return cabState;
    }

    public void setCabState(CabState cabState) {
        this.cabState = cabState;
    }

    @Override
    public String toString() {
        return "CabStatus{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", cabState=" + cabState +
                '}';
    }
}
