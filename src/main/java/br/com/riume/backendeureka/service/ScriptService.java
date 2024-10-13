package br.com.riume.backendeureka.service;

import br.com.riume.backendeureka.DTOs.response.ErrorResponse;
import br.com.riume.backendeureka.model.Client;
import br.com.riume.backendeureka.model.Script;
import br.com.riume.backendeureka.model.Status;
import br.com.riume.backendeureka.repository.ClientRepository;
import br.com.riume.backendeureka.repository.ScriptRepository;
import br.com.riume.backendeureka.repository.StatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ScriptService {

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private DocumentService documentService;

    public ResponseEntity<Object> saveScript(MultipartFile file, String email) {
        try {
            Optional<Client> clientOpt = clientRepository.findByEmail(email);
            if (clientOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(HttpStatus.NOT_FOUND, "Error: Client not found with email: " + email));
            }

            Client client = clientOpt.get();
            Status defaultStatus = statusRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Error: Default status not found"));

            String documentUrl = documentService.upload(file);
            Script script = Script.builder()
                    .client(client)
                    .status(defaultStatus)
                    .document(documentUrl)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(scriptRepository.save(script));

        } catch (Exception e) {
            log.error("Error saving script: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while saving the script"));
        }
    }
}

