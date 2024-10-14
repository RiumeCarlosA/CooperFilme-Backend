package br.com.riume.backendeureka.controller;

import br.com.riume.backendeureka.DTOs.request.ChangeStatusRequest;
import br.com.riume.backendeureka.service.ScriptService;
import br.com.riume.backendeureka.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
@Slf4j
public class StatusController {

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private StatusService statusService;

    @PostMapping("/upload-status")
    public ResponseEntity<Object> uploadStatus(@RequestBody ChangeStatusRequest statusRequest) {
        return statusService.alterStatus(statusRequest);
    }
}
