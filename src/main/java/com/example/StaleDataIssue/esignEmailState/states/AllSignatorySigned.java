package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.entities.ContractEnvelope;
import com.example.StaleDataIssue.entities.ContractEnvelopeRepository;
import com.example.StaleDataIssue.entities.ContractEnvelopeStatus;
import com.example.StaleDataIssue.entities.Signatory;
import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@RequiredArgsConstructor
public class AllSignatorySigned implements EsignEmailState {

    private final EsignEmailStateFactory emailStateFactory;
    private final ContractEnvelopeRepository contractEnvelopeRepository;

    @Override
    public EsignEmailState nextState(ContractEnvelope contractEnvelope, Signatory signatory) {
        return emailStateFactory.createState("EndState");
    }

    @Override
    @SneakyThrows
    public void doAction(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        List<String> allSignatoryEmails = contractEnvelope.getSignatories().stream()
                .map(Signatory::getEmail)
                .toList();

        contractEnvelope.setEnvelopeStatus(ContractEnvelopeStatus.EXECUTED);
        contractEnvelopeRepository.save(contractEnvelope);

        for(String email: allSignatoryEmails) {
            System.out.println("Sending completion email to " + email);
        }
    }
}
