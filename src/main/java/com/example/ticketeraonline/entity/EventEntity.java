package com.example.ticketeraonline.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private VenueEntity venue;
}
