package com.example.CycleSharingSystemBackend.service;

import com.example.CycleSharingSystemBackend.holder.StationDistance;
import com.example.CycleSharingSystemBackend.model.Station;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.List;

public interface StationService {
    Station addStation(Station station);

    List<Station> getAllStations();

    //    List<Station> findNearestStations(double latitude, double longitude, int radius) throws IOException, InterruptedException, ApiException;
    List<StationDistance> findNearestStations(double latitude, double longitude, int radius) throws IOException, InterruptedException, ApiException;

}
