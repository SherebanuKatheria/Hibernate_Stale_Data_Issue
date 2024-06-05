package com.example.StaleDataIssue.esignEmailState;

import com.example.StaleDataIssue.entities.ContractEnvelope;
import com.example.StaleDataIssue.entities.Signatory;
import com.example.StaleDataIssue.esignEmailState.entities.CurrentEsignEmailState;
import com.example.StaleDataIssue.esignEmailState.entities.CurrentEsignEmailStateRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EsignEmailStateService {
    private final CurrentEsignEmailStateRepository currentEmailStateRepository;
    private final EsignEmailStateFactory emailStateFactory;

    public CurrentEsignEmailState getCurrentState(String contractEnvelopeId) {
        if(!currentEmailStateRepository.existsByEnvelopeId(contractEnvelopeId)){
            currentEmailStateRepository.save(CurrentEsignEmailState.builder()
                    .envelopeId(contractEnvelopeId)
                    .build());
        }
        CurrentEsignEmailState stateObject  = currentEmailStateRepository.findByEnvelopeId(contractEnvelopeId);
        return stateObject;
    }

    @SneakyThrows
    @Transactional
    public void nextState(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        CurrentEsignEmailState currentEsignEmailState = getCurrentState(contractEnvelope.getContractEnvelopId());
        EsignEmailState currentState = emailStateFactory.createState(currentEsignEmailState.getCurrentState());
        System.out.println("Current State:" + currentState.getClass().getSimpleName());
        if (currentState != null) {
            EsignEmailState newCurrentState = currentState.nextState(contractEnvelope, signatory);
            System.out.println("New Current State: " + newCurrentState.getClass().getSimpleName());
            saveCurrentState(contractEnvelope.getContractEnvelopId(), newCurrentState.getClass().getSimpleName());
            System.out.println("Current state from Database: " + currentEmailStateRepository.findByEnvelopeId(contractEnvelope.getContractEnvelopId()).getCurrentState());
            newCurrentState.doAction(contractEnvelope, signatory, request);
        }
    }

    @Transactional
    private void saveCurrentState(String contractEnvelopeId, String state) {
        currentEmailStateRepository.updateCurrentState(contractEnvelopeId, state);
    }
}
