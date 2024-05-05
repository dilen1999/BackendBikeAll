package com.example.CycleSharingSystemBackend.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Data
public class FareSettings {
    @Id


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double hourlyRate;
    private double dailyRate;
    private double weeklyRate;
    private double monthlyRate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}