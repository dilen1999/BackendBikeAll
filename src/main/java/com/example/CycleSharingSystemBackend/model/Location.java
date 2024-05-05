package com.example.CycleSharingSystemBackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Location {
    @Id
    private String locationId;
    private double latitude;
    private double longitude;


}
