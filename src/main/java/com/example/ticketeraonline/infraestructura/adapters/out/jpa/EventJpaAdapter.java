package com.example.ticketeraonline.infraestructura.adapters.out.jpa;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.dominio.puertos.out.EventRepositoryPort;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.EventEntity;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper.EventMapper;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository.EventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventJpaAdapter implements EventRepositoryPort {

    private final EventJpaRepository eventJpaRepository;
    private final EventMapper eventMapper;

    @Override
    public List<Event> findAll() {
        return eventMapper.toDomainList(eventJpaRepository.findAll());
    }

    @Override
    public Event findById(Long id) {
        return eventJpaRepository.findById(id)
                .map(eventMapper::toDomain)
                .orElse(null);
    }

    @Override
    public Event save(Event event) {
        EventEntity eventEntity = eventMapper.toEntity(event);
        return eventMapper.toDomain(eventJpaRepository.save(eventEntity));
    }

    @Override
    public Event update(Long id, Event event) {
        if (eventJpaRepository.existsById(id)) {
            EventEntity eventEntity = eventMapper.toEntity(event);
            eventEntity.setId(id); // Ensure the ID is set for update
            return eventMapper.toDomain(eventJpaRepository.save(eventEntity));
        }
        return null; // Or throw an exception as per business rules
    }

    @Override
    public void delete(Long id) {
        eventJpaRepository.deleteById(id);
    }
}
