package com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime startDateTime; // Changed from dateTime
    private LocalDateTime endDateTime;   // New field

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id") // This will be the foreign key column in the events table
    private VenueEntity venue;
}
