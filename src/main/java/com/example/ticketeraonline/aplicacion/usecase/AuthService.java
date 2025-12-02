package com.example.ticketeraonline.aplicacion.usecase;

import com.example.ticketeraonline.dominio.User;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.RoleEntity;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.entity.UserEntity;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository.RoleRepository;
import com.example.ticketeraonline.infraestructura.adapters.out.jpa.repository.UserRepository;
import com.example.ticketeraonline.infraestructura.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity userRole = roleRepository.findByName(RoleEntity.RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        userEntity.setRoles(roles);

        userRepository.save(userEntity);
        return user;
    }

    public String login(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        return jwtProvider.generateToken(authentication);
    }
}
