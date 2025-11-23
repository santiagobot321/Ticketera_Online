package com.example.ticketeraonline.infraestructura.config;

import com.example.ticketeraonline.dominio.puertos.in.EventUseCasePort;
import com.example.ticketeraonline.dominio.puertos.in.VenueUseCasePort;
import com.example.ticketeraonline.dominio.puertos.out.EventRepositoryPort;
import com.example.ticketeraonline.dominio.puertos.out.VenueRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    // Spring will automatically detect EventUseCase and VenueUseCase due to @Service annotation
    // and provide them as implementations for EventUseCasePort and VenueUseCasePort.
    // No explicit @Bean methods are needed here for the use cases themselves.
    // However, we still need to ensure that the adapters (implementing the RepositoryPorts) are available as beans.
    // Since EventJpaAdapter and VenueJpaAdapter are @Component, they will be picked up automatically.
    // The use cases will then be able to autowire the RepositoryPorts.
}
