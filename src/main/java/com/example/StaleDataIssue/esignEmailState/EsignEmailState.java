package com.example.StaleDataIssue.esignEmailState;

import com.example.StaleDataIssue.entities.ContractEnvelope;
import com.example.StaleDataIssue.entities.Signatory;
import jakarta.servlet.http.HttpServletRequest;

public interface EsignEmailState {
    EsignEmailState nextState(ContractEnvelope contractEnvelope, Signatory signatory);
    void doAction(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request);
}
