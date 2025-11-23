package com.example.ticketeraonline.dominio.puertos.out;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.dominio.Venue;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryPort {
    List<Event> findAll();
    Event findById(Long id);
    Event save(Event event);
    Event update(Long id, Event event);
    void delete(Long id);

    // New query methods
    List<Event> findByVenue(Venue venue);
    List<Event> findByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Event> findByVenueAndDateTimeBetween(Venue venue, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
