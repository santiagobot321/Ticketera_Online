package com.example.ticketeraonline.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTO {

    private Long id;

    @NotBlank(message = "The event name cannot be blank")
    @Size(min = 3, max = 100, message = "Event name must be between 3 and 100 characters")
    private String name;

    @Future(message = "The event date must be in the future")
    private LocalDateTime dateTime;

    @NotNull(message = "The venueId is required")
    private Long venueId;
}
