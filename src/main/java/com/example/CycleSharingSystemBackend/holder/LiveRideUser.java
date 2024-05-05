package com.example.CycleSharingSystemBackend.holder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LiveRideUser {
    private Long userId;
    private double latitude;
    private double longitude;
}
