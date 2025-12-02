package com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository;

import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.EventEntity;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Long> {

    @EntityGraph(attributePaths = "venue")
    List<EventEntity> findByVenue(VenueEntity venue);

    @EntityGraph(attributePaths = "venue")
    List<EventEntity> findByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @EntityGraph(attributePaths = "venue")
    List<EventEntity> findByVenueAndDateTimeBetween(VenueEntity venue, LocalDateTime startDateTime, LocalDateTime endDateTime);

    // For dynamic filtering, we might later use JpaSpecificationExecutor or custom queries.
    // For now, these cover basic filtering needs.
}
