package com.example.ticketeraonline.aplicacion.usecase;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.in.VenueUseCasePort;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import Transactional

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional // Apply transactional to the whole class
public class VenueUseCase implements VenueUseCasePort {

    private final VenueRepositoryPort venueRepositoryPort;

    @Override
    @Transactional(readOnly = true) // Mark as read-only transaction
    public List<Venue> getAllVenues() {
        return venueRepositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true) // Mark as read-only transaction
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

        return venueRepositoryPort.save(existingVenue); // Save the updated existing venue
    }


    @Override
    public void deleteVenue(Long id) {
        Venue venue = venueRepositoryPort.findById(id);
        if (venue == null) {
            throw new IllegalArgumentException("Venue not found with ID: " + id);
        }
        venueRepositoryPort.delete(id);
    }

    // New query methods implementation
    @Override
    @Transactional(readOnly = true) // Mark as read-only transaction
    public List<Venue> getVenuesByCapacityGreaterThanEqual(int capacity) {
        return venueRepositoryPort.findByCapacityGreaterThanEqual(capacity);
    }

    @Override
    @Transactional(readOnly = true) // Mark as read-only transaction
    public List<Venue> getVenuesByNameContaining(String name) {
        return venueRepositoryPort.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true) // Mark as read-only transaction
    public List<Venue> getVenuesByAddressContaining(String address) {
        return venueRepositoryPort.findByAddressContainingIgnoreCase(address);
    }
}
