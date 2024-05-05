package com.example.CycleSharingSystemBackend.controller;

import com.example.CycleSharingSystemBackend.holder.StationDistance;
import com.example.CycleSharingSystemBackend.model.Station;
import com.example.CycleSharingSystemBackend.repository.Stationrepository;
import com.example.CycleSharingSystemBackend.service.LocationService;
import com.example.CycleSharingSystemBackend.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.Location;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/station")

public class StationController {

    @Autowired
    private StationService stationService;
    @Autowired
    private LocationService locationService;
    @PostMapping(value = "/addStation")
    public Station newStation(@RequestBody Station station) {
        Location savedLocation = locationService.addLocation(station.getLocation());
        station.setLocation(savedLocation);
        return stationService.addStation(station);
    }

    @GetMapping(value = "/getStations")
    public List<Station> getAllStations() {

        return stationService.getAllStations();
    }

//    @GetMapping("/nearest")
//    public ResponseEntity<List<Station>> findNearestStations(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int radius) {
//        try {
//            List<Station> nearestStations = stationService.findNearestStations(latitude, longitude, radius);
//            return ResponseEntity.ok(nearestStations);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/nearest")
    public ResponseEntity<List<StationDistance>> findNearestStations(@RequestParam double latitude, @RequestParam double longitude, @RequestParam int radius) {
        try {
            List<StationDistance> nearestStations = stationService.findNearestStations(latitude, longitude, radius);
            return ResponseEntity.ok(nearestStations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
