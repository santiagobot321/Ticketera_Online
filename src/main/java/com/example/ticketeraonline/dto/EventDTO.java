package com.example.ticketeraonline.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    private Long id;

    @NotBlank(message = "Event name cannot be empty")
    private String name;

    @NotNull(message = "Event dateTime is required")
    @Future(message = "Event dateTime must be in the future")
    private LocalDateTime dateTime;

    @NotNull(message = "Venue ID is required")
    private Long venueId;
}
