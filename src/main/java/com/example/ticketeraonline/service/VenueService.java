package com.example.ticketeraonline.service;

import com.example.ticketeraonline.dto.VenueDTO;
import com.example.ticketeraonline.entity.VenueEntity;
import com.example.ticketeraonline.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public VenueEntity create(VenueDTO dto) {
        VenueEntity v = new VenueEntity();
        v.setName(dto.getName());
        v.setCity(dto.getCity());
        v.setAddress(dto.getAddress());
        v.setCapacity(dto.getCapacity());
        return venueRepository.save(v);
    }

    public List<VenueEntity> findAll() {
        return venueRepository.findAll();
    }

    public VenueEntity findById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }
}
