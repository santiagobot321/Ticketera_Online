package com.example.ticketeraonline.dominio.puertos.out;

import com.example.ticketeraonline.dominio.Venue;

import java.util.List;

public interface VenueRepositoryPort {
    List<Venue> findAll();
    Venue findById(Long id);
    Venue save(Venue venue);
    Venue update(Long id, Venue venue);
    void delete(Long id);
}
