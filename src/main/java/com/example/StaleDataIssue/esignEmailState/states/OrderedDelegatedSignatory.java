package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.entities.ContractEnvelope;
import com.example.StaleDataIssue.entities.Signatory;
import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderedDelegatedSignatory implements EsignEmailState {

    private final EsignEmailStateFactory emailStateFactory;

    @Override
    public EsignEmailState nextState(ContractEnvelope contractEnvelope, Signatory signatory) {
        return emailStateFactory.createState("OrderedEmailState");
    }

    @Override
    public void doAction(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        sendEmail(signatory);
    }

    private void sendEmail(Signatory signatory) {
        System.out.println("Sending ordered delegated invite email to " + signatory.getName());
    }
}
