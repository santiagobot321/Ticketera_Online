package com.example.ticketeraonline.controller;

import com.example.ticketeraonline.dto.VenueDTO;
import com.example.ticketeraonline.service.VenueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
@Tag(name = "Venues", description = "CRUD for venue management")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    // GET ALL
    @Operation(
            summary = "Get all venues",
            description = "Returns a list of all venues currently stored in memory."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of venues retrieved successfully"
    )
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAllVenues() {
        List<VenueDTO> venues = venueService.getAllVenues();
        return ResponseEntity.ok(venues);
    }

    // GET BY ID
    @Operation(
            summary = "Get venue by ID",
            description = "Returns a venue matching the provided ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Venue retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Venue not found"
    )
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getVenueById(@PathVariable Long id) {
        VenueDTO venue = venueService.getById(id);
        return ResponseEntity.ok(venue);
    }

    // POST
    @Operation(
            summary = "Create a new venue",
            description = "Creates a venue with the given data and returns the resulting object."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Venue created successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid venue data"
    )
    @PostMapping
    public ResponseEntity<VenueDTO> createVenue(
            @RequestBody VenueDTO venueDTO
    ) {
        try {
            VenueDTO createdVenue = venueService.createVenue(venueDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVenue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT
    @Operation(
            summary = "Update an existing venue",
            description = "Updates the data of a venue identified by its ID."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Venue updated successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Venue not found"
    )
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> updateVenue(
            @PathVariable Long id,
            @RequestBody VenueDTO venueDTO
    ) {
        try {
            VenueDTO updatedVenue = venueService.updateVenue(id, venueDTO);
            return ResponseEntity.ok(updatedVenue);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @Operation(
            summary = "Delete venue",
            description = "Deletes a venue by its ID."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Venue deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Venue not found"
    )
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
