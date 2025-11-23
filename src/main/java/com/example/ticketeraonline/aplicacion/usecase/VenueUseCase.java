package com.example.ticketeraonline.aplicacion.usecase;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.in.VenueUseCasePort;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;

import java.util.List;

public class VenueUseCase implements VenueUseCasePort {

    private final VenueRepositoryPort venueRepositoryPort;

    public VenueUseCase(VenueRepositoryPort venueRepositoryPort) {
        this.venueRepositoryPort = venueRepositoryPort;
    }

    @Override
    public List<Venue> getAllVenues() {
        return venueRepositoryPort.findAll();
    }

    @Override
    public Venue getById(Long id) {
        Venue venue = venueRepositoryPort.findById(id);
        if (venue == null) {
            throw new IllegalArgumentException("Venue not found with the ID: " + id);
        }
        return venue;
    }

    @Override
    public Venue createVenue(Venue venue) {
        if (venue.getName() == null || venue.getName().isEmpty()) {
            throw new IllegalArgumentException("Venue name cannot be empty");
        }
        return venueRepositoryPort.save(venue);
    }

    @Override
    public Venue updateVenue(Long id, Venue venue) {
        if (venue.getName() == null || venue.getName().isEmpty()) {
            throw new IllegalArgumentException("Venue name cannot be empty");
        }

        Venue existingVenue = venueRepositoryPort.findById(id);
        if (existingVenue == null) {
            throw new IllegalArgumentException("Venue not found with ID: " + id);
        }

        // Update fields
        existingVenue.setName(venue.getName());
        existingVenue.setAddress(venue.getAddress());
        existingVenue.setCapacity(venue.getCapacity());

        return existingVenue;
    }


    @Override
    public void deleteVenue(Long id) {
        Venue venue = venueRepositoryPort.findById(id);
        if (venue == null) {
            throw new IllegalArgumentException("Venue not found with ID: " + id);
        }
        venueRepositoryPort.delete(id);
    }
}
