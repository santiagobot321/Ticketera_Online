package com.example.ticketeraonline.controller;

import com.example.ticketeraonline.dto.EventDTO;
import com.example.ticketeraonline.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Get all events")
    @GetMapping
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Operation(summary = "Get event by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @Operation(summary = "Create a new event")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Event created"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO createEvent(@Valid @RequestBody EventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    @Operation(summary = "Update an existing event")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Event updated"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @PutMapping("/{id}")
    public EventDTO updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        return eventService.updateEvent(id, eventDTO);
    }

    @Operation(summary = "Delete an event")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Event deleted"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
