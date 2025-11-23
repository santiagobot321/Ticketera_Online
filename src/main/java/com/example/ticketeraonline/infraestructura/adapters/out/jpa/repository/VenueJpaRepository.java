package com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository;

import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueJpaRepository extends JpaRepository<VenueEntity, Long> {
}
