package com.example.ticketeraonline.aplicacion.usecase;

import com.example.ticketeraonline.dominio.Venue;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueUseCaseTest {

    @Mock
    private VenueRepositoryPort venueRepositoryPort;

    @InjectMocks
    private VenueUseCase venueUseCase;

    private Venue venue;

    @BeforeEach
    void setUp() {
        venue = new Venue(1L, "Test Venue", "Test Address", 100, new ArrayList<>());
    }

    @Test
    void getAllVenues_shouldReturnListOfVenues() {
        when(venueRepositoryPort.findAll()).thenReturn(Arrays.asList(venue));

        List<Venue> venues = venueUseCase.getAllVenues();

        assertNotNull(venues);
        assertFalse(venues.isEmpty());
        assertEquals(1, venues.size());
        verify(venueRepositoryPort, times(1)).findAll();
    }

    @Test
    void getById_shouldReturnVenue_whenVenueExists() {
        when(venueRepositoryPort.findById(1L)).thenReturn(venue);

        Venue foundVenue = venueUseCase.getById(1L);

        assertNotNull(foundVenue);
        assertEquals(venue.getId(), foundVenue.getId());
        verify(venueRepositoryPort, times(1)).findById(1L);
    }

    @Test
    void getById_shouldThrowException_whenVenueDoesNotExist() {
        when(venueRepositoryPort.findById(anyLong())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            venueUseCase.getById(99L);
        });

        assertEquals("Venue not found with the ID: 99", exception.getMessage());
        verify(venueRepositoryPort, times(1)).findById(99L);
    }

    @Test
    void createVenue_shouldCreateVenue() {
        Venue newVenue = new Venue(null, "New Venue", "New Address", 200, new ArrayList<>());
        when(venueRepositoryPort.save(any(Venue.class))).thenReturn(venue);

        Venue createdVenue = venueUseCase.createVenue(newVenue);

        assertNotNull(createdVenue);
        assertEquals(venue.getId(), createdVenue.getId());
        verify(venueRepositoryPort, times(1)).save(any(Venue.class));
    }

    @Test
    void createVenue_shouldThrowException_whenVenueNameIsEmpty() {
        Venue newVenue = new Venue(null, "", "New Address", 200, new ArrayList<>());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            venueUseCase.createVenue(newVenue);
        });

        assertEquals("Venue name cannot be empty", exception.getMessage());
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }

    @Test
    void updateVenue_shouldUpdateVenue_whenVenueExists() {
        Venue updatedDetails = new Venue(1L, "Updated Venue", "Updated Address", 150, new ArrayList<>());
        when(venueRepositoryPort.findById(1L)).thenReturn(venue); // Return the existing venue
        when(venueRepositoryPort.save(any(Venue.class))).thenReturn(updatedDetails); // Return the updated venue

        Venue result = venueUseCase.updateVenue(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Updated Venue", result.getName());
        assertEquals("Updated Address", result.getAddress());
        assertEquals(150, result.getCapacity());
        verify(venueRepositoryPort, times(1)).findById(1L);
        verify(venueRepositoryPort, times(1)).save(any(Venue.class));
    }

    @Test
    void updateVenue_shouldThrowException_whenVenueNameIsEmpty() {
        Venue updatedDetails = new Venue(1L, "", "Updated Address", 150, new ArrayList<>());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            venueUseCase.updateVenue(1L, updatedDetails);
        });

        assertEquals("Venue name cannot be empty", exception.getMessage());
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }

    @Test
    void updateVenue_shouldThrowException_whenVenueDoesNotExist() {
        Venue updatedDetails = new Venue(99L, "Updated Venue", "Updated Address", 150, new ArrayList<>());
        when(venueRepositoryPort.findById(anyLong())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            venueUseCase.updateVenue(99L, updatedDetails);
        });

        assertEquals("Venue not found with ID: 99", exception.getMessage());
        verify(venueRepositoryPort, times(1)).findById(99L);
        verify(venueRepositoryPort, never()).save(any(Venue.class));
    }

    @Test
    void deleteVenue_shouldDeleteVenue_whenVenueExists() {
        when(venueRepositoryPort.findById(1L)).thenReturn(venue);
        doNothing().when(venueRepositoryPort).delete(1L);

        venueUseCase.deleteVenue(1L);

        verify(venueRepositoryPort, times(1)).findById(1L);
        verify(venueRepositoryPort, times(1)).delete(1L);
    }

    @Test
    void deleteVenue_shouldThrowException_whenVenueDoesNotExist() {
        when(venueRepositoryPort.findById(anyLong())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            venueUseCase.deleteVenue(99L);
        });

        assertEquals("Venue not found with ID: 99", exception.getMessage());
        verify(venueRepositoryPort, times(1)).findById(99L);
        verify(venueRepositoryPort, never()).delete(anyLong());
    }

    @Test
    void getVenuesByCapacityGreaterThanEqual_shouldReturnListOfVenues() {
        when(venueRepositoryPort.findByCapacityGreaterThanEqual(50)).thenReturn(Arrays.asList(venue));

        List<Venue> venues = venueUseCase.getVenuesByCapacityGreaterThanEqual(50);

        assertNotNull(venues);
        assertFalse(venues.isEmpty());
        assertEquals(1, venues.size());
        verify(venueRepositoryPort, times(1)).findByCapacityGreaterThanEqual(50);
    }

    @Test
    void getVenuesByNameContaining_shouldReturnListOfVenues() {
        when(venueRepositoryPort.findByNameContainingIgnoreCase("test")).thenReturn(Arrays.asList(venue));

        List<Venue> venues = venueUseCase.getVenuesByNameContaining("test");

        assertNotNull(venues);
        assertFalse(venues.isEmpty());
        assertEquals(1, venues.size());
        verify(venueRepositoryPort, times(1)).findByNameContainingIgnoreCase("test");
    }

    @Test
    void getVenuesByAddressContaining_shouldReturnListOfVenues() {
        when(venueRepositoryPort.findByAddressContainingIgnoreCase("address")).thenReturn(Arrays.asList(venue));

        List<Venue> venues = venueUseCase.getVenuesByAddressContaining("address");

        assertNotNull(venues);
        assertFalse(venues.isEmpty());
        assertEquals(1, venues.size());
        verify(venueRepositoryPort, times(1)).findByAddressContainingIgnoreCase("address");
    }
}
