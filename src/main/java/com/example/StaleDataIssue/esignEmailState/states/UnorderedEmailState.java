package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.entities.*;
import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@RequiredArgsConstructor
public class UnorderedEmailState implements EsignEmailState {

    private final EsignEmailStateFactory emailStateFactory;
    private final ContractEnvelopeRepository contractEnvelopeRepository;

    @Override
    @SneakyThrows
    public EsignEmailState nextState() {
        if(checkIfAllSignatoriesHasSigned())
            return emailStateFactory.createState("AllSignatorySigned");
        return emailStateFactory.createState("UnorderedDelegatedSignatory");
    }

    private boolean checkIfAllSignatoriesHasSigned(){
        ContractEnvelope envelope = contractEnvelopeRepository.getReferenceById("contractEnvelope1");
        List<Signatory> signatories = envelope.getSignatories();
        return signatories.stream().allMatch(Signatory::isSigned);
    }
}
