package com.example.ticketeraonline.repository;

import com.example.ticketeraonline.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
