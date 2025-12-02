package com.example.ticketeraonline.infraestructura.adapters.in.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {

    @NotBlank(message = "Event name cannot be blank.")
    @Size(min = 3, max = 100, message = "Event name must be between 3 and 100 characters.")
    private String name;

    @NotNull(message = "Event date and time cannot be null.")
    @Future(message = "Event date and time must be in the future.")
    private LocalDateTime dateTime;

    @NotNull(message = "Venue ID cannot be null.")
    private Long venueId;
}
