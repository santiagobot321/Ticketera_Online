package com.example.ticketeraonline.aplicacion.usecase;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.in.EventUseCasePort;
import com.example.ticketeraonline.dominio.puertos.out.EventRepositoryPort;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventUseCase implements EventUseCasePort {
    private final EventRepositoryPort eventRepositoryPort;
    private final VenueRepositoryPort venueRepositoryPort;

    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        return eventRepositoryPort.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Event getEventById(Long id) {
        Event event = eventRepositoryPort.findById(id);
        if (event == null) {
            throw new IllegalArgumentException("Event not found with id: " + id);
        }
        return event;
    }

    @Override
    public Event createEvent(Event event) {
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }
        // Ensure the venue exists before creating the event
        if (event.getVenue() != null && event.getVenue().getId() != null) {
            Venue existingVenue = venueRepositoryPort.findById(event.getVenue().getId());
            if (existingVenue == null) {
                throw new IllegalArgumentException("Venue not found with id: " + event.getVenue().getId());
            }
            event.setVenue(existingVenue); // Set the managed venue object
        }
        return eventRepositoryPort.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }
        // Ensure the venue exists before updating the event
        if (event.getVenue() != null && event.getVenue().getId() != null) {
            Venue existingVenue = venueRepositoryPort.findById(event.getVenue().getId());
            if (existingVenue == null) {
                throw new IllegalArgumentException("Venue not found with id: " + event.getVenue().getId());
            }
            event.setVenue(existingVenue); // Set the managed venue object
        }

        Event updatedEvent = eventRepositoryPort.update(id, event);
        if (updatedEvent == null) {
            throw new IllegalArgumentException("Event not found with id: " + id);
        }
        return updatedEvent;
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = eventRepositoryPort.findById(id);
        if (event == null) {
            throw new IllegalArgumentException("Event not found with id: " + id);
        }
        eventRepositoryPort.delete(id);
    }

    // New query methods implementation
    @Override
    @Transactional(readOnly = true)
    public List<Event> getEventsByVenueId(Long venueId) {
        Venue venue = venueRepositoryPort.findById(venueId);
        if (venue == null) {
            throw new IllegalArgumentException("Venue not found with id: " + venueId);
        }
        return eventRepositoryPort.findByVenue(venue);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getEventsByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eventRepositoryPort.findByStartDateTimeBetween(startDateTime, endDateTime);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Event> getEventsByVenueIdAndDateTimeBetween(Long venueId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Venue venue = venueRepositoryPort.findById(venueId);
        if (venue == null) {
            throw new IllegalArgumentException("Venue not found with id: " + venueId);
        }
        return eventRepositoryPort.findByVenueAndStartDateTimeBetween(venue, startDateTime, endDateTime);
    }
}
