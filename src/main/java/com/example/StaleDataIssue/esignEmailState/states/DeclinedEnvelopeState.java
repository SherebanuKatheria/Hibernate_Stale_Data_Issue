package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.entities.ContractEnvelope;
import com.example.StaleDataIssue.entities.Signatory;
import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeclinedEnvelopeState implements EsignEmailState {

    private final EsignEmailStateFactory emailStateFactory;

    @Override
    public EsignEmailState nextState(ContractEnvelope contractEnvelope, Signatory signatory) {
        return emailStateFactory.createState("EndState");
    }

    @Override
    public void doAction(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        System.out.println("Sending declination email");
    }
}
