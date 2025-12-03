package com.example.ticketeraonline.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long id;
    private String name;
    private String description; // Added missing description field
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Venue venue;
}
