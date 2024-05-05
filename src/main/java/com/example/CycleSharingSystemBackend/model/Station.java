package com.example.CycleSharingSystemBackend.model;

import jakarta.persistence.*;
import lombok.*;

import javax.xml.stream.Location;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


@Entity

public class Station {
    @Id
    private String stationId;
    private String name;
    @OneToOne
    private Location location;
    private int availableBike;
    private int capacity;

}
