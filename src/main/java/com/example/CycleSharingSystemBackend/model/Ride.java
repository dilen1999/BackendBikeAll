package com.example.CycleSharingSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rideId;

    private Long userId;

    private String startStationId;

    private String endStationId;

    private boolean enride;

    private LocalDateTime startTime = LocalDateTime.now();

    private LocalDateTime endTime;

    @Getter
    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL)
    private List<RidePath> ridePaths = new ArrayList<>();

    ;
}