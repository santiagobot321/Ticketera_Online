package com.example.ticketeraonline.dominio.puertos.out;

import com.example.ticketeraonline.dominio.User;

public interface UserRepositoryPort {
    User save(User user);
    boolean existsByUsername(String username);
}
