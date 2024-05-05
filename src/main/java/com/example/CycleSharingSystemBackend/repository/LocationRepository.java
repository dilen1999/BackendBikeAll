package com.example.CycleSharingSystemBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.stream.Location;

public interface LocationRepository extends JpaRepository <Location, String> {
    Location findByLatitudeAndLongitude(Double latitude, Double longitude);

    Location findByLocationId(String locationId);
}
