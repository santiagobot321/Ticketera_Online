package com.example.ticketeraonline.service;

import com.example.ticketeraonline.dto.EventDTO;
import com.example.ticketeraonline.entity.EventEntity;
import com.example.ticketeraonline.entity.VenueEntity;
import com.example.ticketeraonline.repository.EventRepository;
import com.example.ticketeraonline.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    // Convert Entity → DTO
    private EventDTO toDTO(EventEntity entity) {
        EventDTO dto = new EventDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDateTime(entity.getDateTime());
        dto.setVenueId(entity.getVenue().getId());
        return dto;
    }

    // Convert DTO → Entity
    private EventEntity toEntity(EventDTO dto, VenueEntity venue) {
        EventEntity entity = new EventEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDateTime(dto.getDateTime());
        entity.setVenue(venue);
        return entity;
    }

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EventDTO getEventById(Long id) {
        EventEntity entity = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
        return toDTO(entity);
    }

    public EventDTO createEvent(EventDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }

        // make sure venue exists
        VenueEntity venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new IllegalArgumentException("Venue not found with id: " + dto.getVenueId()));

        EventEntity entity = toEntity(dto, venue);
        EventEntity saved = eventRepository.save(entity);

        return toDTO(saved);
    }

    public EventDTO updateEvent(Long id, EventDTO dto) {
        EventEntity existing = eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }

        VenueEntity venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new IllegalArgumentException("Venue not found with id: " + dto.getVenueId()));

        existing.setName(dto.getName());
        existing.setDateTime(dto.getDateTime());
        existing.setVenue(venue);

        EventEntity saved = eventRepository.save(existing);
        return toDTO(saved);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new IllegalArgumentException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }
}
