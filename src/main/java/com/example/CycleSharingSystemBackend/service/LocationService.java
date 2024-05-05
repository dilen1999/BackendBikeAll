package com.example.CycleSharingSystemBackend.service;

import javax.xml.stream.Location;

public interface LocationService {
    Location addLocation(Location location);

    Location getLocation(Double latitude, Double longitude);

    Location findByLocationId(String locationId);

    Location updateLocation(Location updatedLocation);
}
