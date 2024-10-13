package br.com.riume.backendeureka.service;

import br.com.riume.backendeureka.model.*;
import br.com.riume.backendeureka.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public ResponseEntity<Object> alterarStatus(UUID IdScript, Long newStatusId, UUID userId) {
        Status newStatus = statusRepository.findById(newStatusId).orElseThrow(() -> new RuntimeException("Error: new status not found"));
        Script script = scriptRepository.findById(IdScript).orElseThrow(() -> new RuntimeException("Error: Script not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User not found"));
        if (!canIChangeStatus(script.getStatus(), newStatus, userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Transição de status não permitida.");
        }

        script.setStatus(newStatus);
        scriptRepository.save(script);

        StatusFlow log = StatusFlow.builder()
                .script(script)
                .statusFrom(script.getStatus())
                .statusTo(newStatus)
                .user(user)
                .description("Descrição da transição de status, se necessário")
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
