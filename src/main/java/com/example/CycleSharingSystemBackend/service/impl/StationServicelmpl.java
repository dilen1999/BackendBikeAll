package com.example.CycleSharingSystemBackend.service.impl;

import com.example.CycleSharingSystemBackend.holder.StationDistance;
import com.example.CycleSharingSystemBackend.model.Station;
import com.example.CycleSharingSystemBackend.repository.Stationrepository;
import com.example.CycleSharingSystemBackend.service.StationService;
import com.google.maps.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class StationServicelmpl implements StationService {
    @Value("${google.maps.api.key}")
    private String apiKey;
    @Autowired
    private Stationrepository stationRepository;
    private final AtomicLong currentId = new AtomicLong(0);
    private String generateStationId() {
        return "ST" + currentId.incrementAndGet();
    }

    @Override
    public Station addStation(Station station) {
        String newStationId = generateStationId();
        station.setStationId(newStationId);
        return stationRepository.save(station);
    }

    @Override
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    @Override
    public List<StationDistance> findNearestStations(double latitude, double longitude, int radius) throws IOException, InterruptedException, ApiException {
        // Radius of the Earth in kilometers
        final double earthRadius = 6371;

        // Convert latitude and longitude from degrees to radians
        double lat1 = Math.toRadians(latitude);
        double lon1 = Math.toRadians(longitude);

        List<Station> allStations = getAllStations();
        Map<Station, Double> stationDistances = new HashMap<>();

        for (Station station : allStations) {
            double lat2 = Math.toRadians(station.getLocation().getLatitude());
            double lon2 = Math.toRadians(station.getLocation().getLongitude());

            // Haversine formula
            double dlon = lon2 - lon1;
            double dlat = lat2 - lat1;
            double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            double distance = earthRadius * c;

            // Check if the station is within the given radius
            if (distance <= radius) {
                stationDistances.put(station, distance);
            }
        }

        // Sort stations by distance
        List<Map.Entry<Station, Double>> sortedStations = new ArrayList<>(stationDistances.entrySet());
        sortedStations.sort(Map.Entry.comparingByValue());

        // Extract sorted stations
//        List<Station> nearestStations = new ArrayList<>();
//        for (Map.Entry<Station, Double> entry : sortedStations) {
//            Station station = entry.getKey();
//            Double distance = entry.getValue();
//            nearestStations.add(entry.getKey());
//        }
        List<StationDistance> nearestStations = new ArrayList<>();
        for (Map.Entry<Station, Double> entry : sortedStations) {
            Station station = entry.getKey();
            Double distanceKilometers = entry.getValue();
            double distanceMeters = Math.round(distanceKilometers * 1000 * 100.0) / 100.0;
            nearestStations.add(new StationDistance(station, distanceMeters));
        }

        return nearestStations;
    }


}
