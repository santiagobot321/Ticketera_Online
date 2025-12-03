package com.example.ticketeraonline.infraestructura.adapters.in.web.dto;

import com.example.ticketeraonline.infraestructura.adapters.in.web.validation.StartBeforeEnd;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@StartBeforeEnd(message = "{event.startBeforeEnd}") // Custom cross-field validation
public class EventRequest {

    @NotBlank(message = "{event.name.notBlank}")
    @Size(min = 3, max = 100, message = "{event.name.size}")
    private String name;

    @NotNull(message = "{event.startDateTime.notNull}")
    @Future(message = "{event.startDateTime.future}")
    private LocalDateTime startDateTime;

    @NotNull(message = "{event.endDateTime.notNull}")
    @Future(message = "{event.endDateTime.future}")
    private LocalDateTime endDateTime;

    @NotNull(message = "{event.venueId.notNull}")
    private Long venueId;
}
