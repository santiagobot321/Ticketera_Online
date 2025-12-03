package com.example.ticketeraonline.infraestructura.adapters.in.web;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.in.VenueUseCasePort;
import com.example.ticketeraonline.infraestructura.adapters.in.web.dto.VenueRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
@RequiredArgsConstructor
public class VenueController {

    private final VenueUseCasePort venueUseCasePort;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Venue>> getAllVenues() {
        return ResponseEntity.ok(venueUseCasePort.getAllVenues());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        return ResponseEntity.ok(venueUseCasePort.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Venue> createVenue(@Valid @RequestBody VenueRequest venueRequest) {
        Venue venue = new Venue();
        venue.setName(venueRequest.getName());
        venue.setAddress(venueRequest.getAddress());
        venue.setCapacity(venueRequest.getCapacity());

        Venue createdVenue = venueUseCasePort.createVenue(venue);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVenue);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Venue> updateVenue(@PathVariable Long id, @Valid @RequestBody VenueRequest venueRequest) {
        Venue venue = new Venue();
        venue.setName(venueRequest.getName());
        venue.setAddress(venueRequest.getAddress());
        venue.setCapacity(venueRequest.getCapacity());

        Venue updatedVenue = venueUseCasePort.updateVenue(id, venue);
        return ResponseEntity.ok(updatedVenue);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        venueUseCasePort.deleteVenue(id);
        return ResponseEntity.noContent().build();
    }

    // New query endpoints
    @GetMapping("/by-capacity")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Venue>> getVenuesByCapacity(@RequestParam int capacity) {
        return ResponseEntity.ok(venueUseCasePort.getVenuesByCapacityGreaterThanEqual(capacity));
    }

    @GetMapping("/by-name")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Venue>> getVenuesByName(@RequestParam String name) {
        return ResponseEntity.ok(venueUseCasePort.getVenuesByNameContaining(name));
    }

    @GetMapping("/by-address")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Venue>> getVenuesByAddress(@RequestParam String address) {
        return ResponseEntity.ok(venueUseCasePort.getVenuesByAddressContaining(address));
    }
}
