package br.com.riume.backendeureka.controller;

import br.com.riume.backendeureka.service.ClientService;
import br.com.riume.backendeureka.service.ScriptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/script")
@Slf4j
public class ScriptController {

    @Autowired
    private ScriptService scriptService;


    @PostMapping("/upload-script")
    public ResponseEntity<Object> uploadScript(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) {
        return scriptService.saveScript(file, email);
    }
}
