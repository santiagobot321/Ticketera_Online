package com.example.ticketeraonline.infraestructura.adapters.in.web;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.in.EventUseCasePort;
import com.example.ticketeraonline.infraestructura.adapters.in.web.dto.EventRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventUseCasePort eventUseCasePort;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(eventUseCasePort.getAllEvents());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return ResponseEntity.ok(eventUseCasePort.getEventById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        Event event = new Event();
        event.setName(eventRequest.getName());
        event.setStartDateTime(eventRequest.getStartDateTime()); // Changed from dateTime
        event.setEndDateTime(eventRequest.getEndDateTime());     // New field
        Venue venue = new Venue();
        venue.setId(eventRequest.getVenueId());
        event.setVenue(venue);

        Event createdEvent = eventUseCasePort.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @Valid @RequestBody EventRequest eventRequest) {
        Event event = new Event();
        event.setName(eventRequest.getName());
        event.setStartDateTime(eventRequest.getStartDateTime()); // Changed from dateTime
        event.setEndDateTime(eventRequest.getEndDateTime());     // New field
        Venue venue = new Venue();
        venue.setId(eventRequest.getVenueId());
        event.setVenue(venue);

        Event updatedEvent = eventUseCasePort.updateEvent(id, event);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventUseCasePort.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // New query endpoints
    @GetMapping("/by-venue/{venueId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Event>> getEventsByVenue(@PathVariable Long venueId) {
        return ResponseEntity.ok(eventUseCasePort.getEventsByVenueId(venueId));
    }

    @GetMapping("/by-date-range")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Event>> getEventsByDateRange(
            @RequestParam LocalDateTime startDateTime,
            @RequestParam LocalDateTime endDateTime) {
        return ResponseEntity.ok(eventUseCasePort.getEventsByDateTimeBetween(startDateTime, endDateTime));
    }
}
