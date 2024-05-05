package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.model.Ride;
import com.example.CycleSharingSystemBackend.model.RidePath;
import com.example.CycleSharingSystemBackend.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ride")
public class RideController {
    @Autowired
    private RideService rideHistoryService;

    @PostMapping("/add")
    public Ride addRideHistory(@RequestBody Ride rideHistory) {
        return rideHistoryService.saveRideHistory(rideHistory);
    }

    @PostMapping("/start")
    public Ride startRide(@RequestParam Long userId, @RequestParam String startStationId) {
        return rideHistoryService.startRide(userId, startStationId);
    }

    @PostMapping("/{rideId}/end")
    public void endRide(@PathVariable Long rideId, @RequestParam String endStationId) {
        rideHistoryService.endRide(rideId, endStationId);
    }


    @PostMapping("/{rideId}/update-location")
    public ResponseEntity<?> updateRideLocation(@PathVariable Long rideId,
                                                @RequestParam Double latitude,
                                                @RequestParam Double longitude) {
        rideHistoryService.updateRidePath(rideId, latitude, longitude);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{rideId}/path")
    public ResponseEntity<List<RidePath>> getRidePath(@PathVariable Long rideId) {
        List<RidePath> ridePath = rideHistoryService.getRidePath(rideId);
        return ResponseEntity.ok(ridePath);
    }


    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Ride>> getRideHistory(@PathVariable Long userId) {
        List<Ride> rideHistory = rideHistoryService.getRideHistory(userId);
        if (rideHistory.isEmpty()) {
            return ResponseEntity.noContent().build(); // No content found
        } else {
            return ResponseEntity.ok(rideHistory);
        }
    }



}
