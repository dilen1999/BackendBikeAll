package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.model.Ride;
import com.example.CycleSharingSystemBackend.model.RidePath;

import java.util.List;

public interface RideService {
    Ride saveRideHistory(Ride rideHistory);

    void updateRidePath(Long rideId, Double latitude, Double longitude);

    List<RidePath> getRidePath(Long rideId);

    Ride startRide(Long userId, String startStationId);

    void endRide(Long rideId, String endStationId);

    List<Ride> getRideHistory(Long userId);
}
