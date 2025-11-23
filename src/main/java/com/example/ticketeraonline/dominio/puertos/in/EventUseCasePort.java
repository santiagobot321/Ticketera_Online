package com.example.ticketeraonline.dominio.puertos.in;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.dominio.Venue; // Import Venue
import java.time.LocalDateTime;
import java.util.List;

public interface EventUseCasePort {
    List<Event> getAllEvents();
    Event getEventById(Long id);
    Event createEvent(Event event);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);

    // Updated and new query methods
    List<Event> getEventsByVenueId(Long venueId);
    List<Event> getEventsByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Event> getEventsByVenueIdAndDateTimeBetween(Long venueId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
