package com.example.ticketeraonline.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class VenueDTO {

    private Long id;

    @NotBlank(message = "Venue name cannot be blank")
    private String name;

    @NotBlank(message = "Venue address cannot be blank")
    private String address;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;
}
