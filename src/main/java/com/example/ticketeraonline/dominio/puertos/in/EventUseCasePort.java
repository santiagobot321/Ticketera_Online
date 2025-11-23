package com.example.ticketeraonline.dominio.puertos.in;

import com.example.ticketeraonline.dominio.Event;

import java.util.List;

public interface EventUseCasePort {
    List<Event> getAllEvents();
    Event getEventById(Long id);
    Event createEvent(Event event);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
}
