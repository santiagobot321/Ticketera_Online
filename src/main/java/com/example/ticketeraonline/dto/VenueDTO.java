package com.example.ticketeraonline.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VenueDTO {

    private Long id;

    @NotBlank(message = "Venue name cannot be empty")
    private String name;

    @NotBlank(message = "Venue address cannot be empty")
    private String address;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;
}
