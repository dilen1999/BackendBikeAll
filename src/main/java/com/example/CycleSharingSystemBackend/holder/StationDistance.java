package com.example.CycleSharingSystemBackend.holder;

import com.example.CycleSharingSystemBackend.model.Station;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class StationDistance {
    private Station station;
    private double distance;
}
