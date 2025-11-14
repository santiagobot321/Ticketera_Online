package com.example.ticketeraonline.repository;

import com.example.ticketeraonline.dto.EventDTO;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventRepository {

// findAll, findByID, update, delete
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



}
