package br.com.riume.backendeureka.service;

import br.com.riume.backendeureka.DTOs.request.RegisterRequest;
import br.com.riume.backendeureka.DTOs.response.ErrorResponse;
import br.com.riume.backendeureka.DTOs.response.LoginResponse;
import br.com.riume.backendeureka.model.Role;
import br.com.riume.backendeureka.model.User;
import br.com.riume.backendeureka.repository.RoleRepository;
import br.com.riume.backendeureka.repository.UserRepository;
import br.com.riume.backendeureka.security.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtil jwtUtil;

    public ResponseEntity<Object> saveUser(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT, "Error: Email is already in use!"));
        }

        Set<Role> roles = new HashSet<>(roleRepository.findAllById(registerRequest.getRoles()));
        if (roles.size() != registerRequest.getRoles().size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST, "Error: One or more roles are invalid!"));
        }

        User user = User.builder()
                .userId(UUID.randomUUID())
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new LoginResponse(user, jwtUtil.generateToken(user.getEmail())));
    }


    public User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User not found with email: " + email));
    }

}
