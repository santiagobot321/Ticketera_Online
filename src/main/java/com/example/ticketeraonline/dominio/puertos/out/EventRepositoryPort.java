package com.example.ticketeraonline.dominio.puertos.out;

import com.example.ticketeraonline.dominio.Event;

import java.util.List;

public interface EventRepositoryPort {
    List<Event> findAll();
    Event findById(Long id);
    Event save(Event event);
    Event update(Long id, Event event);
    void delete(Long id);
}
