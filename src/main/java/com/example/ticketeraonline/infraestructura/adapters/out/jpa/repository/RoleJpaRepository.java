package com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository;

import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(RoleEntity.RoleName name);
}
