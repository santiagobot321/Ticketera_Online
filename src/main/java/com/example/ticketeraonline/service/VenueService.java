package com.example.ticketeraonline.service;

import com.example.ticketeraonline.dto.VenueDTO;
import com.example.ticketeraonline.entity.VenueEntity;
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
        return venueRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public VenueDTO getById(Long id) {
        VenueEntity venue = venueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venue not found with ID: " + id));
        return toDTO(venue);
    }

    public VenueDTO createVenue(VenueDTO dto) {

        // Task 2: duplicate name validation
        if (venueRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("A venue with this name already exists");
        }

        VenueEntity venue = toEntity(dto);
        VenueEntity saved = venueRepository.save(venue);
        return toDTO(saved);
    }

    public VenueDTO updateVenue(Long id, VenueDTO dto) {

        VenueEntity venue = venueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venue not found with ID: " + id));

        // Task 2: validate duplicates only if the name is changing
        if (!venue.getName().equals(dto.getName())
                && venueRepository.existsByName(dto.getName())) {
            throw new IllegalArgumentException("Another venue already uses this name");
        }

        venue.setName(dto.getName());
        venue.setAddress(dto.getAddress());
        venue.setCapacity(dto.getCapacity());

        VenueEntity saved = venueRepository.save(venue);
        return toDTO(saved);
    }

    public void deleteVenue(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new IllegalArgumentException("Venue not found with ID: " + id);
        }
        venueRepository.deleteById(id);
    }

    private VenueDTO toDTO(VenueEntity venue) {
        VenueDTO dto = new VenueDTO();
        dto.setId(venue.getId());
        dto.setName(venue.getName());
        dto.setAddress(venue.getAddress());
        dto.setCapacity(venue.getCapacity());
        return dto;
    }

    private VenueEntity toEntity(VenueDTO dto) {
        VenueEntity venue = new VenueEntity();
        venue.setId(dto.getId());
        venue.setName(dto.getName());
        venue.setAddress(dto.getAddress());
        venue.setCapacity(dto.getCapacity());
        return venue;
    }
}
