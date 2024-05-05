package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.model.Location;
import com.example.CycleSharingSystemBackend.repository.LocationRepository;
import com.example.CycleSharingSystemBackend.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.concurrent.atomic.AtomicLong;

@Service
public class LocationServicelmpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;
    private static final AtomicLong counter = new AtomicLong(System.currentTimeMillis());

    public static String generateLocationId() {
        return "LOC-" + counter.getAndIncrement();
    }
    @Override
    public Location addLocation(Location location) {
        location.setLocationId(generateLocationId());
        return locationRepository.save(location);
    }

    @Override
    public Location getLocation(Double latitude, Double longitude) {
        return locationRepository.findByLatitudeAndLongitude(latitude, longitude);

    }

    @Override
    public Location findByLocationId(String locationId) {
        return locationRepository.findByLocationId(locationId);
    }


    @Override
    public Location updateLocation(Location updatedLocation) {
        if (updatedLocation != null) {
            return locationRepository.save(updatedLocation);
        } else {
            return null;
        }
    }
}
