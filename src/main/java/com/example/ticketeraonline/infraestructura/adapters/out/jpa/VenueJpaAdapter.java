package com.example.ticketeraonline.infraestructura.adapters.out.jpa;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.VenueEntity;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.mapper.VenueMapper;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository.VenueJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return venueMapper.toDomain(venueJpaRepository.save(venueEntity));
    }

    @Override
    public Venue update(Long id, Venue venue) {
        if (venueJpaRepository.existsById(id)) {
            VenueEntity venueEntity = venueMapper.toEntity(venue);
            venueEntity.setId(id); // Ensure the ID is set for update
            return venueMapper.toDomain(venueJpaRepository.save(venueEntity));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        venueJpaRepository.deleteById(id);
    }
}
