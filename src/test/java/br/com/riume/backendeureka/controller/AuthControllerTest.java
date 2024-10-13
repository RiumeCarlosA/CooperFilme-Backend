package br.com.riume.backendeureka.controller;

import br.com.riume.backendeureka.DTOs.request.LoginRequest;
import br.com.riume.backendeureka.DTOs.request.RegisterRequest;
import br.com.riume.backendeureka.DTOs.response.LoginResponse;
import br.com.riume.backendeureka.model.User;
import br.com.riume.backendeureka.security.JWTUtil;
import br.com.riume.backendeureka.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JWTUtil jwtUtil;

    private User mockUser;

    @BeforeEach
    void setup() {
        mockUser = User.builder()
                .userId(UUID.randomUUID())
                .email("user@example.com")
                .name("User")
                .password("encodedPassword")
                .active(true)
                .deleted(false)
                .build();
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void login_Success() throws Exception {
        Authentication auth = new UsernamePasswordAuthenticationToken("user@example.com", "password");
        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(userService.findByEmail(anyString())).thenReturn(mockUser);
        when(jwtUtil.generateToken(anyString())).thenReturn("mockToken");

        mockMvc.perform(post("/auth/login")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@example.com\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockToken"))
                .andExpect(jsonPath("$.user.email").value("user@example.com"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void login_InvalidCredentials() throws Exception {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        mockMvc.perform(post("/auth/login")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@example.com\", \"password\":\"wrongPassword\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Incorrect email or password"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void register_Success() throws Exception {
        when(userService.saveUser(any(RegisterRequest.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.OK)
                        .body(new LoginResponse(mockUser, "mockToken")));

        mockMvc.perform(post("/auth/register")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@example.com\", \"password\":\"password\", \"name\":\"User\", \"roles\":[1,2]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value("user@example.com"))
                .andExpect(jsonPath("$.token").value("mockToken"));
    }

    @Test
    @WithMockUser(username="user", roles={"USER"})
    void register_InternalServerError() throws Exception {
        when(userService.saveUser(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Error during registration"));

        mockMvc.perform(post("/auth/register")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"user@example.com\", \"password\":\"password\", \"name\":\"User\", \"roles\":[1,2]}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An error occurred during registration"));
    }
}
