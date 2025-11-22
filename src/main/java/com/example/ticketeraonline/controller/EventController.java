package com.example.ticketeraonline.controller;

import com.example.ticketeraonline.dto.EventDTO;
import com.example.ticketeraonline.entity.EventEntity;
import com.example.ticketeraonline.service.EventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public List<EventEntity> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public EventEntity findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping
    public EventEntity create(@Valid @RequestBody EventDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public EventEntity update(@PathVariable Long id, @Valid @RequestBody EventDTO dto) {
        return service.update(id, dto);
    }
}
