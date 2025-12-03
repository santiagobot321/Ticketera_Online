package com.example.ticketeraonline.aplicacion.usecase;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.out.EventRepositoryPort;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventUseCaseTest {

    @Mock
    private EventRepositoryPort eventRepositoryPort;

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private EventUseCase eventUseCase;

    private Event event;
    private Venue venue;

    @BeforeEach
    void setUp() {
        venue = new Venue(1L, "Test Venue", "Test Address", 100, new ArrayList<>());
        event = new Event(1L, "Test Event", "Description", LocalDateTime.now(), LocalDateTime.now().plusHours(2), venue);
    }

    @Test
    void getAllEvents_shouldReturnListOfEvents() {
        when(eventRepositoryPort.findAll()).thenReturn(Arrays.asList(event));

        List<Event> events = eventUseCase.getAllEvents();

        assertNotNull(events);
        assertFalse(events.isEmpty());
        assertEquals(1, events.size());
        verify(eventRepositoryPort, times(1)).findAll();
    }

    @Test
    void getEventById_shouldReturnEvent_whenEventExists() {
        when(eventRepositoryPort.findById(1L)).thenReturn(event);

        Event foundEvent = eventUseCase.getEventById(1L);

        assertNotNull(foundEvent);
        assertEquals(event.getId(), foundEvent.getId());
        verify(eventRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getEventById_shouldThrowException_whenEventDoesNotExist() {
        when(eventRepositoryPort.findById(anyLong())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventUseCase.getEventById(99L);
        });

        assertEquals("Event not found with id: 99", exception.getMessage());
        verify(eventRepositoryPort, times(1)).findById(99L);
    }

    @Test
    void createEvent_shouldCreateEvent_whenVenueExists() {
        Event newEvent = new Event(null, "New Event", "New Description", LocalDateTime.now(), LocalDateTime.now().plusHours(1), venue);
        when(venueRepositoryPort.findById(venue.getId())).thenReturn(venue);
        when(eventRepositoryPort.save(any(Event.class))).thenReturn(event);

        Event createdEvent = eventUseCase.createEvent(newEvent);

        assertNotNull(createdEvent);
        assertEquals(event.getId(), createdEvent.getId());
        verify(venueRepositoryPort, times(1)).findById(venue.getId());
        verify(eventRepositoryPort, times(1)).save(any(Event.class));
    }

    @Test
    void createEvent_shouldThrowException_whenEventNameIsEmpty() {
        Event newEvent = new Event(null, "", "New Description", LocalDateTime.now(), LocalDateTime.now().plusHours(1), venue);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventUseCase.createEvent(newEvent);
        });

        assertEquals("Event name cannot be empty", exception.getMessage());
        verify(eventRepositoryPort, never()).save(any(Event.class));
    }

    @Test
    void createEvent_shouldThrowException_whenVenueDoesNotExist() {
        Event newEvent = new Event(null, "New Event", "New Description", LocalDateTime.now(), LocalDateTime.now().plusHours(1), new Venue(99L, "Non Existent", "Address", 50, new ArrayList<>()));
        when(venueRepositoryPort.findById(99L)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventUseCase.createEvent(newEvent);
        });

        assertEquals("Venue not found with id: 99", exception.getMessage());
        verify(venueRepositoryPort, times(1)).findById(99L);
        verify(eventRepositoryPort, never()).save(any(Event.class));
    }

    @Test
    void updateEvent_shouldUpdateEvent_whenEventAndVenueExist() {
        Event updatedDetails = new Event(1L, "Updated Event", "Updated Description", LocalDateTime.now(), LocalDateTime.now().plusHours(3), venue);
        when(venueRepositoryPort.findById(venue.getId())).thenReturn(venue);
        when(eventRepositoryPort.update(1L, updatedDetails)).thenReturn(updatedDetails);

        Event result = eventUseCase.updateEvent(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Updated Event", result.getName());
        verify(venueRepositoryPort, times(1)).findById(venue.getId());
        verify(eventRepositoryPort, times(1)).update(1L, updatedDetails);
    }

    @Test
    void updateEvent_shouldThrowException_whenEventNameIsEmpty() {
        Event updatedDetails = new Event(1L, "", "Updated Description", LocalDateTime.now(), LocalDateTime.now().plusHours(3), venue);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventUseCase.updateEvent(1L, updatedDetails);
        });

        assertEquals("Event name cannot be empty", exception.getMessage());
        verify(eventRepositoryPort, never()).update(anyLong(), any(Event.class));
    }

    @Test
    void updateEvent_shouldThrowException_whenEventDoesNotExist() {
        Event updatedDetails = new Event(99L, "Updated Event", "Updated Description", LocalDateTime.now(), LocalDateTime.now().plusHours(3), venue);
        when(venueRepositoryPort.findById(venue.getId())).thenReturn(venue);
        when(eventRepositoryPort.update(99L, updatedDetails)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventUseCase.updateEvent(99L, updatedDetails);
        });

        assertEquals("Event not found with id: 99", exception.getMessage());
        verify(venueRepositoryPort, times(1)).findById(venue.getId());
        verify(eventRepositoryPort, times(1)).update(99L, updatedDetails);
    }

    @Test
    void updateEvent_shouldThrowException_whenVenueDoesNotExist() {
        Event updatedDetails = new Event(1L, "Updated Event", "Updated Description", LocalDateTime.now(), LocalDateTime.now().plusHours(3), new Venue(99L, "Non Existent", "Address", 50, new ArrayList<>()));
        when(venueRepositoryPort.findById(99L)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventUseCase.updateEvent(1L, updatedDetails);
        });

        assertEquals("Venue not found with id: 99", exception.getMessage());
        verify(venueRepositoryPort, times(1)).findById(99L);
        verify(eventRepositoryPort, never()).update(anyLong(), any(Event.class));
    }

    @Test
    void deleteEvent_shouldDeleteEvent_whenEventExists() {
        when(eventRepositoryPort.findById(1L)).thenReturn(event);
        doNothing().when(eventRepositoryPort).delete(1L);

        eventUseCase.deleteEvent(1L);

        verify(eventRepositoryPort, times(1)).findById(1L);
        verify(eventRepositoryPort, times(1)).delete(1L);
    }

    @Test
    void deleteEvent_shouldThrowException_whenEventDoesNotExist() {
        when(eventRepositoryPort.findById(anyLong())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventUseCase.deleteEvent(99L);
        });

        assertEquals("Event not found with id: 99", exception.getMessage());
        verify(eventRepositoryPort, times(1)).findById(99L);
        verify(eventRepositoryPort, never()).delete(anyLong());
    }

    @Test
    void getEventsByVenueId_shouldReturnListOfEvents_whenVenueExists() {
        when(venueRepositoryPort.findById(1L)).thenReturn(venue);
        when(eventRepositoryPort.findByVenue(venue)).thenReturn(Arrays.asList(event));

        List<Event> events = eventUseCase.getEventsByVenueId(1L);

        assertNotNull(events);
        assertFalse(events.isEmpty());
        assertEquals(1, events.size());
        verify(venueRepositoryPort, times(1)).findById(1L);
        verify(eventRepositoryPort, times(1)).findByVenue(venue);
    }

    @Test
    void getEventsByVenueId_shouldThrowException_whenVenueDoesNotExist() {
        when(venueRepositoryPort.findById(anyLong())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventUseCase.getEventsByVenueId(99L);
        });

        assertEquals("Venue not found with id: 99", exception.getMessage());
        verify(venueRepositoryPort, times(1)).findById(99L);
        verify(eventRepositoryPort, never()).findByVenue(any(Venue.class));
    }

    @Test
    void getEventsByDateTimeBetween_shouldReturnListOfEvents() {
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        when(eventRepositoryPort.findByStartDateTimeBetween(start, end)).thenReturn(Arrays.asList(event));

        List<Event> events = eventUseCase.getEventsByDateTimeBetween(start, end);

        assertNotNull(events);
        assertFalse(events.isEmpty());
        assertEquals(1, events.size());
        verify(eventRepositoryPort, times(1)).findByStartDateTimeBetween(start, end);
    }

    @Test
    void getEventsByDateTimeBetween_shouldReturnEmptyList_whenNoEventsFound() {
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        when(eventRepositoryPort.findByStartDateTimeBetween(start, end)).thenReturn(Collections.emptyList());

        List<Event> events = eventUseCase.getEventsByDateTimeBetween(start, end);

        assertNotNull(events);
        assertTrue(events.isEmpty());
        verify(eventRepositoryPort, times(1)).findByStartDateTimeBetween(start, end);
    }

    @Test
    void getEventsByVenueIdAndDateTimeBetween_shouldReturnListOfEvents_whenVenueExists() {
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        when(venueRepositoryPort.findById(1L)).thenReturn(venue);
        when(eventRepositoryPort.findByVenueAndStartDateTimeBetween(venue, start, end)).thenReturn(Arrays.asList(event));

        List<Event> events = eventUseCase.getEventsByVenueIdAndDateTimeBetween(1L, start, end);

        assertNotNull(events);
        assertFalse(events.isEmpty());
        assertEquals(1, events.size());
        verify(venueRepositoryPort, times(1)).findById(1L);
        verify(eventRepositoryPort, times(1)).findByVenueAndStartDateTimeBetween(venue, start, end);
    }

    @Test
    void getEventsByVenueIdAndDateTimeBetween_shouldThrowException_whenVenueDoesNotExist() {
        LocalDateTime start = LocalDateTime.now().minusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(1);
        when(venueRepositoryPort.findById(anyLong())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            eventUseCase.getEventsByVenueIdAndDateTimeBetween(99L, start, end);
        });

        assertEquals("Venue not found with id: 99", exception.getMessage());
        verify(venueRepositoryPort, times(1)).findById(99L);
        verify(eventRepositoryPort, never()).findByVenueAndStartDateTimeBetween(any(Venue.class), any(LocalDateTime.class), any(LocalDateTime.class));
    }
}
