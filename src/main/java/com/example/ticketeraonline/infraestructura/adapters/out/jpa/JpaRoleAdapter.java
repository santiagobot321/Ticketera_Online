package com.example.ticketeraonline.infraestructura.adapters.out.jpa;

import com.example.ticketeraonline.dominio.puertos.out.RoleRepositoryPort;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaRoleAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository roleJpaRepository;

    // Methods implementing the port interface will go here
}
