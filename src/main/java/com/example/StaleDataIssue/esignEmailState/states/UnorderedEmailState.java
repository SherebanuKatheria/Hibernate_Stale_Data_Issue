package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.entities.*;
import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;

@RequiredArgsConstructor
public class UnorderedEmailState implements EsignEmailState {

    private final EsignEmailStateFactory emailStateFactory;

    @Override
    @SneakyThrows
    public EsignEmailState nextState(ContractEnvelope contractEnvelope, Signatory signatory) {
        if(contractEnvelope.getEnvelopeStatus().equals(ContractEnvelopeStatus.DECLINED)){
            return emailStateFactory.createState("DeclinedEnvelopeState");
        } else if(signatory != null && signatory.getType().equals(SignatoryType.DELEGATED) && !signatory.isSigned()){
            return emailStateFactory.createState("UnorderedDelegatedSignatory");
        } else if(checkIfAllSignatoriesHasSigned(contractEnvelope)){
            return emailStateFactory.createState("AllSignatorySigned");
        } else
            return emailStateFactory.createState("UnorderedEmailState"); //Return same state
    }

    @Override
    public void doAction(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        if(signatory != null){
            System.out.println("Sending confirmation emails");
        } else {
            sendUnorderedEmails(contractEnvelope, request);
        }
    }

    private void sendUnorderedEmails(ContractEnvelope contractEnvelope, HttpServletRequest request){
        List<Signatory> signatories = contractEnvelope.getSignatories();
        for(Signatory envelopeSignatory : signatories) {
            sendEmail(contractEnvelope, envelopeSignatory, request);
        }
    }

    private void sendEmail(ContractEnvelope contractEnvelope, Signatory envelopeSignatory, HttpServletRequest request) {
        System.out.println("Sending Unordered Email to: " + envelopeSignatory.getName());
    }


    private boolean checkIfAllSignatoriesHasSigned(ContractEnvelope envelope){
        List<Signatory> signatories = envelope.getSignatories();
        return signatories.stream().allMatch(Signatory::isSigned);
    }
}
