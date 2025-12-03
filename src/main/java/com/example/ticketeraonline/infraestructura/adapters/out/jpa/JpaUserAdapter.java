package com.example.ticketeraonline.infraestructura.adapters.out.jpa;

import com.example.ticketeraonline.dominio.User;
import com.example.ticketeraonline.dominio.puertos.out.UserRepositoryPort;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.UserEntity;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaUserAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        // Here you would also set roles, etc.
        userJpaRepository.save(userEntity);
        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }
}
