package com.example.ticketeraonline.service;

import com.example.ticketeraonline.dto.EventDTO;
import com.example.ticketeraonline.entity.EventEntity;
import com.example.ticketeraonline.entity.VenueEntity;
import com.example.ticketeraonline.repository.EventRepository;
import com.example.ticketeraonline.repository.VenueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public EventEntity create(EventDTO dto) {
        if (eventRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Event name already exists");
        }

        VenueEntity venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        EventEntity event = new EventEntity();
        event.setName(dto.getName());
        event.setDateTime(dto.getDateTime());
        event.setVenue(venue);
        event.setCategory(dto.getCategory());

        return eventRepository.save(event);
    }

    public Page<EventEntity> getEvents(String city, String category, String startDate, Pageable pageable) {
        LocalDateTime date = (startDate != null ? LocalDateTime.parse(startDate) : null);
        return eventRepository.filter(city, category, date, pageable);
    }

    public EventEntity findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public EventEntity update(Long id, EventDTO dto) {
        EventEntity event = findById(id);

        if (!event.getName().equals(dto.getName()) && eventRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Event name already exists");
        }

        VenueEntity venue = venueRepository.findById(dto.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        event.setName(dto.getName());
        event.setCategory(dto.getCategory());
        event.setDateTime(dto.getDateTime());
        event.setVenue(venue);

        return eventRepository.save(event);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
