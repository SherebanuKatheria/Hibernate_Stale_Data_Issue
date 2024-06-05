package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.entities.ContractEnvelope;
import com.example.StaleDataIssue.entities.Signatory;
import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class StartState implements EsignEmailState {
    private final EsignEmailStateFactory emailStateFactory;

    @Override
    @SneakyThrows
    public EsignEmailState nextState(ContractEnvelope contractEnvelope, Signatory signatory) {
        if(contractEnvelope.isSendInOrder())
            return emailStateFactory.createState("OrderedEmailState");
        else
            return emailStateFactory.createState("UnorderedEmailState");
    }

    @Override
    public void doAction(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        System.out.println("Starting State");
    }
}