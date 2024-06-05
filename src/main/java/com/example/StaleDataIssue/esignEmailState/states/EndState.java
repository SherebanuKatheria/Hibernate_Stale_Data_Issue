package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.entities.ContractEnvelope;
import com.example.StaleDataIssue.entities.Signatory;
import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import jakarta.servlet.http.HttpServletRequest;

public class EndState implements EsignEmailState {
    @Override
    public EsignEmailState nextState(ContractEnvelope contractEnvelope, Signatory signatory) {
        return null;
    }

    @Override
    public void doAction(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {

    }
}
