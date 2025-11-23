package com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository;

import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueJpaRepository extends JpaRepository<VenueEntity, Long> {

    @EntityGraph(attributePaths = "events")
    List<VenueEntity> findAll(); // Override to eagerly fetch events

    List<VenueEntity> findByCapacityGreaterThanEqual(int capacity);
    List<VenueEntity> findByNameContainingIgnoreCase(String name);
    List<VenueEntity> findByAddressContainingIgnoreCase(String address);
}
