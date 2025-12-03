package com.example.ticketeraonline.dominio;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Event {
    private Long id;
    private String name;
    private LocalDateTime startDateTime; // Changed from dateTime
    private LocalDateTime endDateTime;   // New field
    private Venue venue;
}
