package com.example.ticketeraonline.infraestructura.adapters.in.web;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.in.VenueUseCasePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueUseCasePort venueUseCasePort;

    public VenueController(VenueUseCasePort venueUseCasePort) {
        this.venueUseCasePort = venueUseCasePort;
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Venue>> getAllVenues() {
        List<Venue> venues = venueUseCasePort.getAllVenues();
        return ResponseEntity.ok(venues);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        try {
            Venue venue = venueUseCasePort.getById(id);
            return ResponseEntity.ok(venue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        // Validation required in Task 2
        if (venue.getName() == null || venue.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Venue createdVenue = venueUseCasePort.createVenue(venue);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVenue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Venue> updateVenue(
            @PathVariable Long id,
            @RequestBody Venue venue
    ) {
        // Validation required in Task 2
        if (venue.getName() == null || venue.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Venue updatedVenue = venueUseCasePort.updateVenue(id, venue);
            return ResponseEntity.ok(updatedVenue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        try {
            venueUseCasePort.deleteVenue(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
