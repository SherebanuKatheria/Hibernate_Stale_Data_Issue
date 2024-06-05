package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.entities.ContractEnvelope;
import com.example.StaleDataIssue.entities.Signatory;
import com.example.StaleDataIssue.entities.SignatoryType;
import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class UnorderedDelegatedSignatory implements EsignEmailState {
    private final EsignEmailStateFactory emailStateFactory;

    @Override
    public EsignEmailState nextState(ContractEnvelope contractEnvelope, Signatory signatory) {
        if(signatory != null && signatory.getType().equals(SignatoryType.DELEGATED) && !signatory.isSigned())
            return emailStateFactory.createState("UnorderedDelegatedSignatory");
        else
            return emailStateFactory.createState("UnorderedEmailState");
    }

    @Override
    public void doAction(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        sendEmail(contractEnvelope, signatory, request);
    }

    @SneakyThrows
    private void sendEmail(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        System.out.println("Sending delegated ordered email to " + signatory.getName());
    }
}
