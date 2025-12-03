package com.example.ticketeraonline.controller;

import com.example.ticketeraonline.dominio.Venue;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class VenueControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @BeforeEach
    void setUp() {
        // Clear data before each test
        venueRepositoryPort.findAll().forEach(venue -> venueRepositoryPort.delete(venue.getId()));
    }

    @Test
    void getAllVenues_shouldReturnEmptyList_whenNoVenues() throws Exception {
        mockMvc.perform(get("/venues"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createVenue_shouldReturnCreatedVenue() throws Exception {
        Venue newVenue = new Venue(null, "New Venue", "123 Main St", 150, new ArrayList<>());

        mockMvc.perform(post("/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newVenue)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("New Venue")))
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.capacity", is(150)));
    }

    @Test
    void createVenue_shouldReturnBadRequest_whenVenueNameIsEmpty() throws Exception {
        Venue newVenue = new Venue(null, "", "123 Main St", 150, new ArrayList<>());

        mockMvc.perform(post("/venues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newVenue)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getVenueById_shouldReturnVenue_whenVenueExists() throws Exception {
        Venue savedVenue = venueRepositoryPort.save(new Venue(null, "Existing Venue", "456 Oak Ave", 300, new ArrayList<>()));

        mockMvc.perform(get("/venues/{id}", savedVenue.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedVenue.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Existing Venue")));
    }

    @Test
    void getVenueById_shouldReturnNotFound_whenVenueDoesNotExist() throws Exception {
        mockMvc.perform(get("/venues/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateVenue_shouldReturnUpdatedVenue() throws Exception {
        Venue savedVenue = venueRepositoryPort.save(new Venue(null, "Old Name", "Old Address", 100, new ArrayList<>()));
        Venue updatedVenue = new Venue(savedVenue.getId(), "Updated Name", "Updated Address", 250, new ArrayList<>());

        mockMvc.perform(put("/venues/{id}", savedVenue.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedVenue)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedVenue.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Updated Name")))
                .andExpect(jsonPath("$.address", is("Updated Address")))
                .andExpect(jsonPath("$.capacity", is(250)));
    }

    @Test
    void updateVenue_shouldReturnNotFound_whenVenueDoesNotExist() throws Exception {
        Venue updatedVenue = new Venue(999L, "Non Existent", "Address", 100, new ArrayList<>());

        mockMvc.perform(put("/venues/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedVenue)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteVenue_shouldReturnNoContent_whenVenueExists() throws Exception {
        Venue savedVenue = venueRepositoryPort.save(new Venue(null, "Venue to Delete", "Delete Address", 50, new ArrayList<>()));

        mockMvc.perform(delete("/venues/{id}", savedVenue.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/venues/{id}", savedVenue.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteVenue_shouldReturnNotFound_whenVenueDoesNotExist() throws Exception {
        mockMvc.perform(delete("/venues/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getVenuesByCapacityGreaterThanEqual_shouldReturnVenues() throws Exception {
        venueRepositoryPort.save(new Venue(null, "Small Venue", "Small St", 50, new ArrayList<>()));
        venueRepositoryPort.save(new Venue(null, "Medium Venue", "Medium St", 150, new ArrayList<>()));
        venueRepositoryPort.save(new Venue(null, "Large Venue", "Large St", 300, new ArrayList<>()));

        mockMvc.perform(get("/venues/search/by-capacity")
                        .param("capacity", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Medium Venue")))
                .andExpect(jsonPath("$[1].name", is("Large Venue")));
    }

    @Test
    void getVenuesByNameContaining_shouldReturnVenues() throws Exception {
        venueRepositoryPort.save(new Venue(null, "Concert Hall", "Hall St", 500, new ArrayList<>()));
        venueRepositoryPort.save(new Venue(null, "Conference Center", "Center St", 1000, new ArrayList<>()));
        venueRepositoryPort.save(new Venue(null, "Small Room", "Room St", 20, new ArrayList<>()));

        mockMvc.perform(get("/venues/search/by-name")
                        .param("name", "con"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Concert Hall")))
                .andExpect(jsonPath("$[1].name", is("Conference Center")));
    }

    @Test
    void getVenuesByAddressContaining_shouldReturnVenues() throws Exception {
        venueRepositoryPort.save(new Venue(null, "Venue A", "123 Street A", 100, new ArrayList<>()));
        venueRepositoryPort.save(new Venue(null, "Venue B", "456 Avenue B", 200, new ArrayList<>()));
        venueRepositoryPort.save(new Venue(null, "Venue C", "789 Street C", 300, new ArrayList<>()));

        mockMvc.perform(get("/venues/search/by-address")
                        .param("address", "street"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Venue A")))
                .andExpect(jsonPath("$[1].name", is("Venue C")));
    }
}
