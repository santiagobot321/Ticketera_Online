package com.example.ticketeraonline.aplicacion.usecase;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.dominio.puertos.in.EventUseCasePort;
import com.example.ticketeraonline.dominio.puertos.out.EventRepositoryPort;
import java.util.List;

public class EventUseCase implements EventUseCasePort {
    private final EventRepositoryPort eventRepositoryPort;

    public EventUseCase(EventRepositoryPort eventRepositoryPort) {
        this.eventRepositoryPort = eventRepositoryPort;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepositoryPort.findAll();
    }

    @Override
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
        return eventRepositoryPort.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty");
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
}
