package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.service.LocationService;
import com.example.CycleSharingSystemBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.Location;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
    @PostMapping("/add")
    public Location addLocation(@RequestBody Location location) {

        return locationService.addLocation(location);
    }

    @GetMapping("/{latitude}/{longitude}")
    public Location getLocation(@PathVariable Double latitude, @PathVariable Double longitude) {
        return locationService.getLocation(latitude,longitude);
    }

    @GetMapping("/{locationId}")
    public Location getLocationById(@PathVariable String locationId) {
        return locationService.findByLocationId(locationId);
    }

    @PutMapping("/update")
    public ResponseEntity<Location> updateLocation(
            @RequestBody Location updatedLocation) {
        Location updated = locationService.updateLocation(updatedLocation);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else
        {
            return ResponseEntity.notFound().build();
        }
    }


}
