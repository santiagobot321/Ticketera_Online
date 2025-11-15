package com.example.ticketeraonline.controller;

import com.example.ticketeraonline.dto.VenueDTO;
import com.example.ticketeraonline.service.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        List<VenueDTO> venues = venueService.getAllVenues();
        return ResponseEntity.ok(venues);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        try {
            VenueDTO venue = venueService.getById(id);
            return ResponseEntity.ok(venue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST
    @PostMapping
    public ResponseEntity<VenueDTO> createVenue(@RequestBody VenueDTO venueDTO) {
        // Validation required in Task 2
        if (venueDTO.getName() == null || venueDTO.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            VenueDTO createdVenue = venueService.createVenue(venueDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVenue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> updateVenue(
            @PathVariable Long id,
            @RequestBody VenueDTO venueDTO
    ) {
        // Validation required in Task 2
        if (venueDTO.getName() == null || venueDTO.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            VenueDTO updatedVenue = venueService.updateVenue(id, venueDTO);
            return ResponseEntity.ok(updatedVenue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        try {
            venueService.deleteVenue(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
