package com.example.ticketeraonline.controller;

import com.example.ticketeraonline.dto.VenueDTO;
import com.example.ticketeraonline.entity.VenueEntity;
import com.example.ticketeraonline.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueService service;

    public VenueController(VenueService service) {
        this.service = service;
    }

    @PostMapping
    public VenueEntity create(@Valid @RequestBody VenueDTO dto) {
        return service.create(dto);
    }


    @GetMapping
    public List<VenueEntity> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public VenueEntity findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
