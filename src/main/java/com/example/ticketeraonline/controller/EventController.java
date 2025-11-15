package com.example.ticketeraonline.controller;

import com.example.ticketeraonline.dto.EventDTO;
import com.example.ticketeraonline.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Obtener todos los eventos", description = "Devuelve la lista completa de eventos almacenados en memoria.")
    @ApiResponse(responseCode = "200", description = "Lista recuperada correctamente")
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @Operation(summary = "Obtener un evento por ID",
            description = "Busca un evento por su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe un evento con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(
            @Parameter(description = "ID del evento a recuperar")
            @PathVariable Long id) {
        try {
            EventDTO event = eventService.getEventById(id);
            return ResponseEntity.ok(event);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo evento",
            description = "Agrega un evento al catálogo en memoria. El nombre no puede estar vacío.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento creado"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(
            @Parameter(description = "Datos del evento a crear")
            @RequestBody EventDTO eventDTO) {
        try {
            EventDTO event = eventService.createEvent(eventDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(event);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar un evento existente",
            description = "Modifica los datos de un evento identificado por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento actualizado"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(
            @Parameter(description = "ID del evento a actualizar")
            @PathVariable Long id,
            @Parameter(description = "Nuevos datos del evento")
            @RequestBody EventDTO eventDTO) {
        try {
            EventDTO event = eventService.updateEvent(id, eventDTO);
            return ResponseEntity.ok(event);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un evento",
            description = "Elimina un evento del catálogo usando su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Evento eliminado"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @Parameter(description = "ID del evento a eliminar")
            @PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
