package com.example.StaleDataIssue.esignEmailState;

import com.example.StaleDataIssue.esignEmailState.entities.CurrentEsignEmailState;
import com.example.StaleDataIssue.esignEmailState.entities.CurrentEsignEmailStateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EsignEmailStateService {
    private final CurrentEsignEmailStateRepository currentEmailStateRepository;
    private final EsignEmailStateFactory emailStateFactory;

    public CurrentEsignEmailState getCurrentState() {
        if(!currentEmailStateRepository.existsByEnvelopeId("contractEnvelope1")){
            currentEmailStateRepository.save(CurrentEsignEmailState.builder()
                    .envelopeId("contractEnvelope1")
                    .build());
        }
        CurrentEsignEmailState stateObject  = currentEmailStateRepository.findByEnvelopeId("contractEnvelope1");
        return stateObject;
    }

    @SneakyThrows
    @Transactional
    public void nextState() {
        CurrentEsignEmailState currentEsignEmailState = getCurrentState();
        EsignEmailState currentState = emailStateFactory.createState(currentEsignEmailState.getCurrentState());
        System.out.println("Current State:" + currentState.getClass().getSimpleName());

        EsignEmailState newCurrentState = currentState.nextState();
        System.out.println("New Current State before saving: " + newCurrentState.getClass().getSimpleName());

        saveCurrentState(newCurrentState.getClass().getSimpleName());
        System.out.println("New Current state from Database after saving: " + currentEmailStateRepository.findByEnvelopeId("contractEnvelope1").getCurrentState());
    }

    @Transactional
    private void saveCurrentState(String newstate) {
        currentEmailStateRepository.updateCurrentState("contractEnvelope1", newstate);
    }
}
