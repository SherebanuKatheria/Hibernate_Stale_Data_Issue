package com.example.StaleDataIssue;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final EsignService esignService;

    @GetMapping("/api")
    @SneakyThrows
    public void start(){
        System.out.println(esignService.initiateEsign());
        System.out.println(esignService.delegate());
        System.out.println(esignService.sign());
    }
}
