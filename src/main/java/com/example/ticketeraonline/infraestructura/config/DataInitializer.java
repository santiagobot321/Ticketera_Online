package com.example.ticketeraonline.infraestructura.config;

import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.RoleEntity;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleJpaRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName(RoleEntity.RoleName.ROLE_USER).isEmpty()) {
            RoleEntity userRole = new RoleEntity();
            userRole.setName(RoleEntity.RoleName.ROLE_USER);
            roleRepository.save(userRole);
        }
        if (roleRepository.findByName(RoleEntity.RoleName.ROLE_ADMIN).isEmpty()) {
            RoleEntity adminRole = new RoleEntity();
            adminRole.setName(RoleEntity.RoleName.ROLE_ADMIN);
            roleRepository.save(adminRole);
        }
    }
}
