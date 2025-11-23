package com.example.ticketeraonline.infraestructura.config;

import com.example.ticketeraonline.aplicacion.usecase.EventUseCase;
import com.example.ticketeraonline.aplicacion.usecase.VenueUseCase;
import com.example.ticketeraonline.dominio.puertos.in.EventUseCasePort;
import com.example.ticketeraonline.dominio.puertos.in.VenueUseCasePort;
import com.example.ticketeraonline.dominio.puertos.out.EventRepositoryPort;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public EventUseCasePort eventUseCase(EventRepositoryPort eventRepositoryPort, VenueRepositoryPort venueRepositoryPort) {
        return new EventUseCase(eventRepositoryPort, venueRepositoryPort);
    }

    @Bean
    public VenueUseCasePort venueUseCase(VenueRepositoryPort venueRepositoryPort) {
        return new VenueUseCase(venueRepositoryPort);
    }
}
