package com.example.ticketeraonline.infraestructura.adapters.out.jpa;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.out.EventRepositoryPort;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.EventEntity;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper.EventMapper;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper.VenueMapper;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository.EventJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventJpaAdapter implements EventRepositoryPort {

    private final EventJpaRepository eventJpaRepository;
    private final EventMapper eventMapper;
    private final VenueMapper venueMapper;

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
        if (eventEntity.getVenue() != null) {
            // In a real scenario, you might need to fetch the VenueEntity to ensure it's managed
            // For now, we rely on JPA to handle the existing ID correctly for ManyToOne
        }
        return eventMapper.toDomain(eventJpaRepository.save(eventEntity));
    }

    @Override
    public Event update(Long id, Event event) {
        Optional<EventEntity> existingEventEntityOptional = eventJpaRepository.findById(id);
        if (existingEventEntityOptional.isPresent()) {
            EventEntity existingEventEntity = existingEventEntityOptional.get();
            EventEntity updatedEventEntity = eventMapper.toEntity(event);

            existingEventEntity.setName(updatedEventEntity.getName());
            existingEventEntity.setStartDateTime(updatedEventEntity.getStartDateTime()); // Changed from dateTime
            existingEventEntity.setEndDateTime(updatedEventEntity.getEndDateTime());     // New field
            existingEventEntity.setVenue(updatedEventEntity.getVenue());

            return eventMapper.toDomain(eventJpaRepository.save(existingEventEntity));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        eventJpaRepository.deleteById(id);
    }

    @Override
    public List<Event> findByVenue(Venue venue) {
        return eventMapper.toDomainList(eventJpaRepository.findByVenue(venueMapper.toEntity(venue)));
    }

    @Override
    public List<Event> findByStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eventMapper.toDomainList(eventJpaRepository.findByStartDateTimeBetween(startDateTime, endDateTime));
    }

    @Override
    public List<Event> findByVenueAndStartDateTimeBetween(Venue venue, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eventMapper.toDomainList(eventJpaRepository.findByVenueAndStartDateTimeBetween(venueMapper.toEntity(venue), startDateTime, endDateTime));
    }
}
