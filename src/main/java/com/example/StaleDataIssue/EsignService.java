package com.example.StaleDataIssue;

import com.example.StaleDataIssue.entities.*;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EsignService {

    private final EsignEmailStateService emailStateService;
    private final ContractEnvelopeRepository contractEnvelopeRepository;
    private final SignatoryRepository signatoryRepository;

    public String initiateEsign(HttpServletRequest request) {

        Signatory signatory = Signatory.builder()
                .signatoryId("signatory1")
                .name("name1")
                .email("1@gmail.com")
                .isSigned(false)
                .signatoryOrder(1)
                .type(SignatoryType.NEEDS_TO_SIGN)
                .build();
        signatoryRepository.saveAndFlush(signatory);

        ContractEnvelope envelope = ContractEnvelope.builder()
                .contractEnvelopId("contractEnvelope1")
                .envelopeStatus(ContractEnvelopeStatus.INITIATED)
                .sendInOrder(false)
                .signatories(List.of(signatory))
                .build();
        contractEnvelopeRepository.saveAndFlush(envelope);

        emailStateService.nextState(envelope, null, request);

        return "Initiated";
    }

    public String delegate(HttpServletRequest request){

        ContractEnvelope contractEnvelope = contractEnvelopeRepository.getReferenceById("contractEnvelope1");

        Signatory delegatedSignatory = Signatory.builder()
                .signatoryId("delegatedsignatory")
                .name("name2")
                .email("2@gmail.com")
                .type(SignatoryType.DELEGATED)
                .signatoryOrder(1)
                .isSigned(false)
                .build();
        signatoryRepository.saveAndFlush(delegatedSignatory);

        Signatory oldSignatory = signatoryRepository.getReferenceById("signatory1");
        oldSignatory.setType(SignatoryType.RECIEVES_A_COPY);
        oldSignatory.setSigned(true);
        signatoryRepository.saveAndFlush(oldSignatory);

        List<Signatory> signatories = contractEnvelope.getSignatories();
        signatories.add(delegatedSignatory);
        contractEnvelope.setSignatories(signatories);
        contractEnvelopeRepository.saveAndFlush(contractEnvelope);

        emailStateService.nextState(contractEnvelope, delegatedSignatory, request);

        return "Delegated";
    }

    public String sign(HttpServletRequest request){

        ContractEnvelope contractEnvelope = contractEnvelopeRepository.getReferenceById("contractEnvelope1");
        Signatory signatory = signatoryRepository.getReferenceById("delegatedsignatory");
        signatory.setSigned(true);
        signatoryRepository.saveAndFlush(signatory);

        emailStateService.nextState(contractEnvelope, signatory, request);

        if(allSignatoriesSigned(contractEnvelope)){
            emailStateService.nextState(contractEnvelope, null, null); //Changes state to AllSignatorySigned
        }

        return "Signed";
    }

    private boolean allSignatoriesSigned(ContractEnvelope contractEnvelope) {
        List<Signatory> signatories = contractEnvelope.getSignatories();
        return signatories.stream().allMatch(Signatory::isSigned);
    }

}
