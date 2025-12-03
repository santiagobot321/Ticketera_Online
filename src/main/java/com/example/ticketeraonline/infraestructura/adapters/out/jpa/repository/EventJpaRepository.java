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
    List<EventEntity> findByStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime); // Changed method name

    @EntityGraph(attributePaths = "venue")
    List<EventEntity> findByVenueAndStartDateTimeBetween(VenueEntity venue, LocalDateTime startDateTime, LocalDateTime endDateTime); // Changed method name

    // For dynamic filtering, we might later use JpaSpecificationExecutor or custom queries.
    // For now, these cover basic filtering needs.
}
