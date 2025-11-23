package com.example.ticketeraonline.dominio;



import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {

    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Long venueId;
}
