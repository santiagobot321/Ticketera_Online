package com.example.ticketeraonline.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venue {
    private Long id;
    private String name;
    private String address;
    private int capacity;
    private List<Event> events = new ArrayList<>(); // Added list of events
}
