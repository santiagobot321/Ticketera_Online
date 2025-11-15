package com.example.ticketeraonline.repository;

import com.example.ticketeraonline.dto.VenueDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class VenueRepository {

    private Long nextId = 1L;
    private List<VenueDTO> venues = new ArrayList<>();

    public List<VenueDTO> findAll() {
        return venues;
    };

    public VenueDTO findById (Long id) {
        for (VenueDTO venue : venues ) {
            if (id.equals(venue.getId())) {
                return venue;
            }
        }
        return null;
    }

    public VenueDTO update(Long id, VenueDTO venueDTO) {
        for (VenueDTO venue : venues) {
            if (id.equals(venue.getId())) {
                venue.setName(venueDTO.getName());
                venue.setAddress(venueDTO.getAddress());
                venue.setCapacity(venueDTO.getCapacity());
                return venue;
            }
        }
        return null;
    }


    public VenueDTO save(VenueDTO venueDTO) {
        if (venueDTO.getId() == null) {
            venueDTO.setId(nextId);
            nextId++;
        }
        venues.add(venueDTO);
        return venueDTO;
    }

    public void delete(Long id) {
        venues.removeIf(venue -> id.equals(venue.getId()));
    }
}
