package com.example.ticketeraonline.repository;

import com.example.ticketeraonline.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    boolean existsByName(String name);

    @Query("SELECT e FROM EventEntity e WHERE (:city IS NULL OR e.venue.city = :city) " +
            "AND (:category IS NULL OR e.category = :category) " +
            "AND (:startDate IS NULL OR e.dateTime >= :startDate)")
    Page<EventEntity> filter(String city, String category, LocalDateTime startDate, Pageable pageable);
}
