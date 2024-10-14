package br.com.riume.backendeureka.service;

import br.com.riume.backendeureka.DTOs.request.ChangeStatusRequest;
import br.com.riume.backendeureka.DTOs.response.ErrorResponse;
import br.com.riume.backendeureka.model.*;
import br.com.riume.backendeureka.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class StatusService {

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusTransitionRepository statusTransitionRepository;

    @Autowired
    private StatusFlowRepository statusFlowRepository;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<Object> alterStatus(ChangeStatusRequest changeStatus) {
        Status newStatus = statusRepository.findById(changeStatus.getStatus()).orElseThrow(() -> new RuntimeException("Error: new status not found"));
        Script script = scriptRepository.findById(UUID.fromString(changeStatus.getScriptId())).orElseThrow(() -> new RuntimeException("Error: Script not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getName().toLowerCase().contains("anonymous")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Error: Unauthorized"));

        }
        User user = userRepository.findByEmail((String) authentication.getPrincipal()).orElseThrow(() -> new RuntimeException("Error: User not found"));

        if (!canIChangeStatus(script.getStatus(), newStatus, user.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: status change failed.");
        }

        script.setStatus(newStatus);
        scriptRepository.save(script);

        StatusFlow log = StatusFlow.builder()
                .script(script)
                .statusFrom(script.getStatus())
                .statusTo(newStatus)
                .user(user)
                .description(changeStatus.getMessage())
                .build();

        statusFlowRepository.save(log);

        return ResponseEntity.ok("Status atualizado com sucesso.");
    }

    public boolean canIChangeStatus(Status currentStatus, Status newStatus, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Role> userRoleIds = new ArrayList<>(user.getRoles());

        boolean isTransitionAllowed = statusTransitionRepository.existsByCurrentStatusAndNextStatusAndRoleIn(
                currentStatus, newStatus, userRoleIds);

        if (!isTransitionAllowed) {
            throw new RuntimeException("You do not have permission to transition to this status.");
        }

        return true;
    }


}
