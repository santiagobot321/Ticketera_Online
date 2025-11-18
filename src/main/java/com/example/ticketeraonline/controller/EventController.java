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
    public List<EventDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @Operation(summary = "Obtener un evento por ID",
            description = "Busca un evento por su identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento encontrado"),
            @ApiResponse(responseCode = "404", description = "No existe un evento con ese ID")
    })
    @GetMapping("/{id}")
    public EventDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @Operation(summary = "Crear un nuevo evento",
            description = "Agrega un evento al catálogo en memoria. El nombre no puede estar vacío.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Evento creado"),
            @ApiResponse(responseCode = "400", description = "Error de validación")
    })
    @PostMapping
    public EventDTO createEvent(@RequestBody EventDTO eventDTO) {
            return eventService.createEvent(eventDTO);
    }

    @Operation(summary = "Actualizar un evento existente",
            description = "Modifica los datos de un evento identificado por ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evento actualizado"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    @PutMapping("/{id}")
    public EventDTO updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
            return eventService.updateEvent(id, eventDTO);
    }

    @Operation(summary = "Eliminar un evento",
            description = "Elimina un evento del catálogo usando su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Evento eliminado"),
            @ApiResponse(responseCode = "404", description = "Evento no encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
            eventService.deleteEvent(id);
    }
}
