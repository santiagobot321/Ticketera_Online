package com.example.ticketeraonline.repository;

import com.example.ticketeraonline.dto.EventDTO;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepository {

    private Long nextId = 1L;
    private List<EventDTO> events = new ArrayList<>();

    public List<EventDTO> findAll() {
        return events;
    };

    public EventDTO findById (Long id) {
        for (EventDTO event : events ) {
            if (id.equals(event.getId())) {
                return event;
            }
        }
        return null;
    }

    public EventDTO update(Long id, EventDTO eventDTO) {
        for (EventDTO event : events) {
            if (id.equals(event.getId())) {
                event.setName(eventDTO.getName());
                event.setDateTime(eventDTO.getDateTime());
                event.setVenueId(eventDTO.getVenueId());
                return event;
            }
        }
        return null;
    }

    public EventDTO save(EventDTO eventDTO) {
        if (eventDTO.getId() == null) {
            eventDTO.setId(nextId);
            nextId++;
        }
        events.add(eventDTO);
        return eventDTO;
    }

    public void delete(Long id) {
        events.removeIf(event -> id.equals(event.getId()));
    }
}