package com.example.StaleDataIssue;

import com.example.StaleDataIssue.esignEmailState.entities.CurrentEsignEmailState;
import com.example.StaleDataIssue.esignEmailState.entities.CurrentEsignEmailStateRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateDataLoad {

    private final CurrentEsignEmailStateRepository currentEsignEmailStateRepository;

    @PostConstruct
    private void initialize() {
        staleDataLoad();
    }

    @Transactional
    @SneakyThrows
    private void staleDataLoad() {
        CurrentEsignEmailState currentEsignEmailState = CurrentEsignEmailState.builder().id(1).envelopeId("env1").currentState("State1").build();
        currentEsignEmailStateRepository.save(currentEsignEmailState);
        System.out.println(currentEsignEmailStateRepository.findByEnvelopeId("env1"));

        currentEsignEmailStateRepository.updateCurrentState("env1", "State2");
        System.out.println(currentEsignEmailStateRepository.findByEnvelopeId("env1"));

        currentEsignEmailStateRepository.updateCurrentState("env1", "State3");
        System.out.println(currentEsignEmailStateRepository.findByEnvelopeId("env1"));

        currentEsignEmailStateRepository.updateCurrentState("env1", "State4");
        System.out.println(currentEsignEmailStateRepository.findByEnvelopeId("env1"));
    }
}
