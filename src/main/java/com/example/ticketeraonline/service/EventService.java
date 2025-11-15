package com.example.ticketeraonline.service;

import com.example.ticketeraonline.dto.EventDTO;
import com.example.ticketeraonline.repository.EventRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<EventDTO> getAllEvents() {
        return EventRepository.findAll();
    }

    public EventDTO getEventById(Long id) {
        EventDTO event = eventRepository.findById(id);
        if (event == null) {
            throw new IllegalArgumentException("Event not found with id: " + id);
        }
        return event;
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        if (eventDTO.getName() == null || eventDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }
        return eventRepository.save(eventDTO);
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        if (eventDTO.getName() == null || eventDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
        }

        EventDTO updatedEvent = eventRepository.update(id, eventDTO);
        if (updatedEvent == null) {
            throw new IllegalArgumentException("Event not found with id: " + id);
        }
        return updatedEvent;
    }

    public void deleteEvent(Long id) {
        EventDTO eventDTO = eventRepository.findById(id);
        if (eventDTO == null) {
            throw new IllegalArgumentException("Event not found with id: " + id);
        }
        eventRepository.delete(id);
    }
}