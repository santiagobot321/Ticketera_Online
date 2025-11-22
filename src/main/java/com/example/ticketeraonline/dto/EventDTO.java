package com.example.ticketeraonline.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class EventDTO {

    @NotBlank(message = "Event name cannot be blank")
    @Size(min = 3, max = 50)
    private String name;

    @Future(message = "Event date must be in the future")
    private LocalDateTime dateTime;

    @NotNull(message = "Venue ID is required")
    private Long venueId;

    @NotBlank(message = "Category is required")
    private String category;

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Long getVenueId() {
        return venueId;
    }

    public String getCategory() {
        return category;
    }
}
