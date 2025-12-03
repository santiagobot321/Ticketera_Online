package com.example.ticketeraonline.controller;

import com.example.ticketeraonline.dominio.Event;
import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.out.EventRepositoryPort;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class EventControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EventRepositoryPort eventRepositoryPort; // To clean up or set up data directly
    @Autowired
    private VenueRepositoryPort venueRepositoryPort; // To clean up or set up data directly

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop"); // Ensure schema is created
    }

    private Venue testVenue;

    @BeforeEach
    void setUp() {
        // Clear data before each test
        eventRepositoryPort.findAll().forEach(event -> eventRepositoryPort.delete(event.getId()));
        venueRepositoryPort.findAll().forEach(venue -> venueRepositoryPort.delete(venue.getId()));

        testVenue = new Venue(null, "Integration Test Venue", "123 Test St", 200, new ArrayList<>());
        testVenue = venueRepositoryPort.save(testVenue); // Save to get an ID
    }

    @Test
    void getAllEvents_shouldReturnEmptyList_whenNoEvents() throws Exception {
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createEvent_shouldReturnCreatedEvent() throws Exception {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        Event newEvent = new Event(null, "Concert", "Rock Concert", start, end, testVenue);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Concert")))
                .andExpect(jsonPath("$.venue.id", is(testVenue.getId().intValue())));
    }

    @Test
    void createEvent_shouldReturnBadRequest_whenEventNameIsEmpty() throws Exception {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        Event newEvent = new Event(null, "", "Rock Concert", start, end, testVenue);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getEventById_shouldReturnEvent_whenEventExists() throws Exception {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        Event savedEvent = eventRepositoryPort.save(new Event(null, "Festival", "Music Festival", start, end, testVenue));

        mockMvc.perform(get("/events/{id}", savedEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedEvent.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Festival")));
    }

    @Test
    void getEventById_shouldReturnNotFound_whenEventDoesNotExist() throws Exception {
        mockMvc.perform(get("/events/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEvent_shouldReturnUpdatedEvent() throws Exception {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        Event savedEvent = eventRepositoryPort.save(new Event(null, "Old Name", "Old Desc", start, end, testVenue));

        Event updatedEvent = new Event(savedEvent.getId(), "New Name", "New Description", start.plusDays(1), end.plusDays(1), testVenue);

        mockMvc.perform(put("/events/{id}", savedEvent.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEvent)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedEvent.getId().intValue())))
                .andExpect(jsonPath("$.name", is("New Name")))
                .andExpect(jsonPath("$.description", is("New Description")));
    }

    @Test
    void updateEvent_shouldReturnNotFound_whenEventDoesNotExist() throws Exception {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        Event updatedEvent = new Event(999L, "New Name", "New Description", start, end, testVenue);

        mockMvc.perform(put("/events/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEvent)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEvent_shouldReturnNoContent_whenEventExists() throws Exception {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        Event savedEvent = eventRepositoryPort.save(new Event(null, "Event to Delete", "Desc", start, end, testVenue));

        mockMvc.perform(delete("/events/{id}", savedEvent.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/events/{id}", savedEvent.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEvent_shouldReturnNotFound_whenEventDoesNotExist() throws Exception {
        mockMvc.perform(delete("/events/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getEventsByVenueId_shouldReturnEvents() throws Exception {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        eventRepositoryPort.save(new Event(null, "Event 1", "Desc 1", start, end, testVenue));
        eventRepositoryPort.save(new Event(null, "Event 2", "Desc 2", start.plusDays(1), end.plusDays(1), testVenue));

        mockMvc.perform(get("/events/venue/{venueId}", testVenue.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].venue.id", is(testVenue.getId().intValue())));
    }

    @Test
    void getEventsByDateTimeBetween_shouldReturnEvents() throws Exception {
        LocalDateTime start1 = LocalDateTime.now().plusDays(1);
        LocalDateTime end1 = start1.plusHours(2);
        LocalDateTime start2 = LocalDateTime.now().plusDays(3);
        LocalDateTime end2 = start2.plusHours(2);

        eventRepositoryPort.save(new Event(null, "Event A", "Desc A", start1, end1, testVenue));
        eventRepositoryPort.save(new Event(null, "Event B", "Desc B", start2, end2, testVenue));

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String queryStart = LocalDateTime.now().plusHours(1).format(formatter);
        String queryEnd = LocalDateTime.now().plusDays(2).format(formatter);

        mockMvc.perform(get("/events/search/by-date-range")
                        .param("startDateTime", queryStart)
                        .param("endDateTime", queryEnd))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Event A")));
    }

    @Test
    void getEventsByVenueIdAndDateTimeBetween_shouldReturnEvents() throws Exception {
        LocalDateTime start1 = LocalDateTime.now().plusDays(1);
        LocalDateTime end1 = start1.plusHours(2);
        LocalDateTime start2 = LocalDateTime.now().plusDays(3);
        LocalDateTime end2 = start2.plusHours(2);

        eventRepositoryPort.save(new Event(null, "Event X", "Desc X", start1, end1, testVenue));
        eventRepositoryPort.save(new Event(null, "Event Y", "Desc Y", start2, end2, testVenue));

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String queryStart = LocalDateTime.now().plusHours(1).format(formatter);
        String queryEnd = LocalDateTime.now().plusDays(2).format(formatter);

        mockMvc.perform(get("/events/search/by-venue-and-date-range")
                        .param("venueId", testVenue.getId().toString())
                        .param("startDateTime", queryStart)
                        .param("endDateTime", queryEnd))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Event X")));
    }
}
