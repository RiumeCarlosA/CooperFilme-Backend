package br.com.riume.backendeureka.controller;

import br.com.riume.backendeureka.DTOs.request.RegisterClientRequest;
import br.com.riume.backendeureka.DTOs.response.ErrorResponse;
import br.com.riume.backendeureka.model.Client;
import br.com.riume.backendeureka.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/clients")
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerClient(@RequestBody RegisterClientRequest registerRequest) {
        try {
            return clientService.saveClient(registerRequest);
        } catch (Exception e) {
            log.error("Registration error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during registration"));
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getClientByEmail(@PathVariable String email) {
        try {
            Client client = clientService.findByEmail(email);
            return ResponseEntity.ok(client);
        } catch (RuntimeException e) {
            log.error("Error finding client by email: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()));
        }
    }

}
