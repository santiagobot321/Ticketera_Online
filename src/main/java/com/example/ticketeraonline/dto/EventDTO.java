package com.example.ticketeraonline.dto;



import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Long venueId;
}
