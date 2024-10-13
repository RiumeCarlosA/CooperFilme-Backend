package br.com.riume.backendeureka.service;

import br.com.riume.backendeureka.DTOs.request.RegisterClientRequest;
import br.com.riume.backendeureka.DTOs.request.RegisterRequest;
import br.com.riume.backendeureka.DTOs.response.ErrorResponse;
import br.com.riume.backendeureka.DTOs.response.LoginResponse;
import br.com.riume.backendeureka.model.Client;
import br.com.riume.backendeureka.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ResponseEntity<Object> saveClient(RegisterClientRequest registroRequest) {
        if (clientRepository.findByEmail(registroRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT, "Error: Email is already in use!"));
        }

        Client client = Client.builder()
                .clientId(UUID.randomUUID())
                .name(registroRequest.getName())
                .email(registroRequest.getEmail())
                .phone(registroRequest.getPhone())
                .build();

        clientRepository.save(client);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Client saved successfully");
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: Client not found with email: " + email));
    }
}
