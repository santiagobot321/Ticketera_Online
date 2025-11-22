package com.example.ticketeraonline.service;

import com.example.ticketeraonline.dto.EventDTO;
import com.example.ticketeraonline.entity.EventEntity;
import com.example.ticketeraonline.entity.VenueEntity;
import com.example.ticketeraonline.repository.EventRepository;
import com.example.ticketeraonline.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public EventEntity create(EventDTO dto) {
        VenueEntity venue = venueRepository.findById(dto.getVenueId()).orElse(null);

        EventEntity e = new EventEntity();
        e.setName(dto.getName());
        e.setDateTime(dto.getDateTime());
        e.setVenue(venue);

        return eventRepository.save(e);
    }

    public List<EventEntity> findAll() {
        return eventRepository.findAll();
    }

    public EventEntity findById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public EventEntity update(Long id, EventDTO dto) {
        EventEntity e = eventRepository.findById(id).orElse(null);
        if (e == null) return null;

        e.setName(dto.getName());
        e.setDateTime(dto.getDateTime());
        e.setVenue(venueRepository.findById(dto.getVenueId()).orElse(null));

        return eventRepository.save(e);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public EventEntity create(EventDTO dto) {

        if (eventRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Event name already exists");
        }

        VenueEntity venue = venueRepository.findById(dto.getVenueId()).orElse(null);

        EventEntity e = new EventEntity();
        e.setName(dto.getName());
        e.setDateTime(dto.getDateTime());
        e.setVenue(venue);

        return eventRepository.save(e);
    }

    public EventEntity update(Long id, EventDTO dto) {

        EventEntity existing = eventRepository.findById(id).orElse(null);
        if (existing == null) return null;

        if (!existing.getName().equals(dto.getName()) &&
                eventRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Event name already exists");
        }

        existing.setName(dto.getName());
        existing.setDateTime(dto.getDateTime());
        existing.setVenue(venueRepository.findById(dto.getVenueId()).orElse(null));

        return eventRepository.save(existing);
    }

}
