package com.example.StaleDataIssue;

import com.example.StaleDataIssue.entities.*;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EsignService {

    private final EsignEmailStateService emailStateService;
    private final ContractEnvelopeRepository contractEnvelopeRepository;
    private final SignatoryRepository signatoryRepository;

    public String initiateEsign() {

        Signatory signatory = Signatory.builder()
                .signatoryId("signatory1")
                .name("name1")
                .isSigned(false)
                .build();
        signatoryRepository.saveAndFlush(signatory);

        ContractEnvelope envelope = ContractEnvelope.builder()
                .contractEnvelopId("contractEnvelope1")
                .signatories(List.of(signatory))
                .build();
        contractEnvelopeRepository.saveAndFlush(envelope);

        emailStateService.nextState();

        return "Initiated";
    }

    @Transactional
    public String delegate(){

        ContractEnvelope contractEnvelope = contractEnvelopeRepository.getReferenceById("contractEnvelope1");

        Signatory delegatedSignatory = Signatory.builder()
                .signatoryId("delegatedsignatory")
                .name("name2")
                .isSigned(false)
                .type("Delegated")
                .build();
        signatoryRepository.saveAndFlush(delegatedSignatory);

        Signatory oldSignatory = signatoryRepository.getReferenceById("signatory1");
        oldSignatory.setSigned(true);
        signatoryRepository.saveAndFlush(oldSignatory);

        List<Signatory> signatories = contractEnvelope.getSignatories();
        signatories.add(delegatedSignatory);
        contractEnvelope.setSignatories(signatories);
        contractEnvelopeRepository.saveAndFlush(contractEnvelope);

        emailStateService.nextState();

        return "Delegated";
    }

    @SneakyThrows
    public String sign(){

        Signatory signatory = signatoryRepository.getReferenceById("delegatedsignatory");
        signatory.setSigned(true);
        signatoryRepository.saveAndFlush(signatory);

        emailStateService.nextState();

        emailStateService.nextState(); //Changes state to AllSignatorySigned

        return "Signed";
    }
}
