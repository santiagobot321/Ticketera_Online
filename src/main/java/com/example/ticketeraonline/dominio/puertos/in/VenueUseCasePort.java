package com.example.ticketeraonline.dominio.puertos.in;

import com.example.ticketeraonline.dominio.Venue;

import java.util.List;

public interface VenueUseCasePort {
    List<Venue> getAllVenues();
    Venue getById(Long id);
    Venue createVenue(Venue venue);
    Venue updateVenue(Long id, Venue venue);
    void deleteVenue(Long id);
}
