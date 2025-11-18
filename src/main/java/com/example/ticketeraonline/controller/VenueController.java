package com.example.ticketeraonline.controller;

import com.example.ticketeraonline.dto.VenueDTO;
import com.example.ticketeraonline.service.VenueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public List<VenueDTO> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public VenueDTO getVenueById(@PathVariable Long id) {
        return venueService.getById(id);
    }

    @PostMapping
    public VenueDTO createVenue(@RequestBody VenueDTO venueDTO) {
        return venueService.createVenue(venueDTO);
    }

    @PutMapping("/{id}")
    public VenueDTO updateVenue(@PathVariable Long id, @RequestBody VenueDTO venueDTO) {
        return venueService.updateVenue(id, venueDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
    }
}
