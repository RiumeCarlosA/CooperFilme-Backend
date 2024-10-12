package br.com.riume.backendeureka.controller;

import br.com.riume.backendeureka.DTOs.request.LoginRequest;
import br.com.riume.backendeureka.DTOs.request.RegisterRequest;
import br.com.riume.backendeureka.DTOs.response.ErrorResponse;
import br.com.riume.backendeureka.DTOs.response.LoginResponse;
import br.com.riume.backendeureka.model.User;
import br.com.riume.backendeureka.security.JWTUtil;
import br.com.riume.backendeureka.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            User user = userService.findByEmail(authentication.getName());
            if (user.isDeleted() || !user.isActivated()) {
                ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN, "User has been deactivated");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }
            String token = jwtUtil.generateToken(user.getEmail());
            LoginResponse response = new LoginResponse(user, token);
            log.info("User {} logged in successfully", user.getEmail());
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            log.error("Invalid login attempt for user {}", request.getEmail());
            ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Incorrect email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during login");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            return userService.saveUser(request);
        } catch (Exception e) {
            log.error("Registration error: {}", e.getMessage());
            ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during registration");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
