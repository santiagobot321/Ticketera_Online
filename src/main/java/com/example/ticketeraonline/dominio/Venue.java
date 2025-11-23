package com.example.ticketeraonline.dominio;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Venue {
    private Long id;
    private String name;
    private String address;
    private int capacity;
    private List<Event> events = new ArrayList<>(); // Added list of events
}
