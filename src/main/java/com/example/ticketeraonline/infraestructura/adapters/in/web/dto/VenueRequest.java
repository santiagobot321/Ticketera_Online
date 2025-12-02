package com.example.ticketeraonline.infraestructura.adapters.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VenueRequest {

    @NotBlank(message = "Venue name cannot be blank.")
    @Size(min = 3, max = 100, message = "Venue name must be between 3 and 100 characters.")
    private String name;

    @NotBlank(message = "Venue address cannot be blank.")
    @Size(min = 5, max = 255, message = "Venue address must be between 5 and 255 characters.")
    private String address;

    @NotNull(message = "Venue capacity cannot be null.")
    @Min(value = 1, message = "Venue capacity must be at least 1.")
    private Integer capacity;
}
