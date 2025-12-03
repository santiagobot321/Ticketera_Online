package com.example.ticketeraonline.dominio.puertos.out;

import com.example.ticketeraonline.dominio.Venue;

import java.util.List;

public interface VenueRepositoryPort {
    List<Venue> findAll();
    Venue findById(Long id);
    Venue save(Venue venue);
    Venue update(Long id, Venue venue);
    void delete(Long id);

    // New query methods
    List<Venue> findByCapacityGreaterThanEqual(int capacity);
    List<Venue> findByNameContainingIgnoreCase(String name);
    List<Venue> findByAddressContainingIgnoreCase(String address);
}
