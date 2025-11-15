package com.example.ticketeraonline.service;

import com.example.ticketeraonline.dto.EventDTO;
import com.example.ticketeraonline.dto.VenueDTO;
import com.example.ticketeraonline.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll();
    }

    public VenueDTO getById(Long id) {
        VenueDTO venueDTO = venueRepository.findById(id);
        if (venueDTO == null) {
            throw new IllegalArgumentException("Venue not found with the ID: " + id);
        }
        return venueDTO;
    }

    public VenueDTO createVenue(VenueDTO venueDTO) {
        if (venueDTO.getName() == null || venueDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Venue name cannot be empty");
        }
        return venueRepository.save(venueDTO);
    }

    public VenueDTO updateVenue(Long id, VenueDTO venueDTO) {
        if (venueDTO.getName() == null || venueDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Venue name cannot be empty");
        }

        VenueDTO existingVenue = venueRepository.findById(id);
        if (existingVenue == null) {
            throw new IllegalArgumentException("Venue not found with ID: " + id);
        }

        // Update fields
        existingVenue.setName(venueDTO.getName());
        existingVenue.setAddress(venueDTO.getAddress());
        existingVenue.setCapacity(venueDTO.getCapacity());

        return existingVenue;
    }


    public void deleteVenue(Long id) {
        VenueDTO venueDTO = venueRepository.findById(id);
        if (venueDTO == null) {
            throw new IllegalArgumentException("Venue not found with ID: " + id);
        }
        venueRepository.delete(id);
    }
}
