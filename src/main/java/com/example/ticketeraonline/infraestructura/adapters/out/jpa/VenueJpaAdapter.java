package com.example.ticketeraonline.infraestructura.adapters.out.jpa;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.VenueEntity;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper.VenueMapper;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository.VenueJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VenueJpaAdapter implements VenueRepositoryPort {

    private final VenueJpaRepository venueJpaRepository;
    private final VenueMapper venueMapper;

    @Override
    public List<Venue> findAll() {
        return venueMapper.toDomainList(venueJpaRepository.findAll());
    }

    @Override
    public Venue findById(Long id) {
        return venueJpaRepository.findById(id)
                .map(venueMapper::toDomain)
                .orElse(null);
    }

    @Override
    public Venue save(Venue venue) {
        VenueEntity venueEntity = venueMapper.toEntity(venue);
        // Ensure bidirectional relationship is set for new events
        if (venueEntity.getEvents() != null) {
            venueEntity.getEvents().forEach(event -> event.setVenue(venueEntity));
        }
        return venueMapper.toDomain(venueJpaRepository.save(venueEntity));
    }

    @Override
    public Venue update(Long id, Venue venue) {
        Optional<VenueEntity> existingVenueEntityOptional = venueJpaRepository.findById(id);
        if (existingVenueEntityOptional.isPresent()) {
            VenueEntity existingVenueEntity = existingVenueEntityOptional.get();
            VenueEntity updatedVenueEntity = venueMapper.toEntity(venue); // Map incoming domain to a temporary entity

            // Update scalar properties
            existingVenueEntity.setName(updatedVenueEntity.getName());
            existingVenueEntity.setAddress(updatedVenueEntity.getAddress());
            existingVenueEntity.setCapacity(updatedVenueEntity.getCapacity());

            // Update the OneToMany relationship (events)
            // This is a common pattern to handle additions/removals with orphanRemoval
            existingVenueEntity.getEvents().clear(); // Clear existing events
            if (updatedVenueEntity.getEvents() != null) {
                updatedVenueEntity.getEvents().forEach(event -> {
                    event.setVenue(existingVenueEntity); // Set bidirectional relationship
                    existingVenueEntity.getEvents().add(event); // Add to the managed collection
                });
            }

            return venueMapper.toDomain(venueJpaRepository.save(existingVenueEntity));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        venueJpaRepository.deleteById(id);
    }

    // New query methods implementation
    @Override
    public List<Venue> findByCapacityGreaterThanEqual(int capacity) {
        return venueMapper.toDomainList(venueJpaRepository.findByCapacityGreaterThanEqual(capacity));
    }

    @Override
    public List<Venue> findByNameContainingIgnoreCase(String name) {
        return venueMapper.toDomainList(venueJpaRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public List<Venue> findByAddressContainingIgnoreCase(String address) {
        return venueMapper.toDomainList(venueJpaRepository.findByAddressContainingIgnoreCase(address));
    }
}
