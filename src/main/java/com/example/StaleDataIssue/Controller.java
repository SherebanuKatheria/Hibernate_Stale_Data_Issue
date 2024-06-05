package com.example.StaleDataIssue;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final EsignService service;

    @GetMapping("/api/initiate/unordered")
    public ResponseEntity<String> unordered(HttpServletRequest request){
        String response = service.initiateEsign(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/delegate/unordered")
    public ResponseEntity<String> delegate(HttpServletRequest request){
        String response = service.delegate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/sign/unordered")
    public ResponseEntity<String> sign(HttpServletRequest request){
        String response = service.sign(request);
        return ResponseEntity.ok(response);
    }

}
