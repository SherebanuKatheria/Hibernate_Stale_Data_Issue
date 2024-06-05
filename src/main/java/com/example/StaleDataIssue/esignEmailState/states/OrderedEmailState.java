package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.entities.*;
import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class OrderedEmailState implements EsignEmailState {

    private final EsignEmailStateFactory emailStateFactory;
    private final SignatoryRepository signatoryRepository;

    @Override
    public EsignEmailState nextState(ContractEnvelope contractEnvelope, Signatory signatory) {
        if(contractEnvelope.getEnvelopeStatus().equals(ContractEnvelopeStatus.DECLINED)){
            return emailStateFactory.createState("DeclinedEnvelopeState");
        } else if(signatory != null && signatory.getType().equals(SignatoryType.DELEGATED) && !signatory.isSigned()){
            return emailStateFactory.createState("OrderedDelegatedSignatory");
        } else if(checkIfAllSignatoriesHasSigned(contractEnvelope)){
            return emailStateFactory.createState("AllSignatorySigned");
        } else
            return emailStateFactory.createState("OrderedEmailState"); //Return same state
    }

    @Override
    public void doAction(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        if(signatory != null){ //It means someone has signed
            System.out.println("sending ordered confirmation email to " + signatory.getName());

            if(!checkIfAllSignatoriesHasSigned(contractEnvelope)){
                sendOrderedEmails(contractEnvelope, request);
            }
        } else {
            sendOrderedEmails(contractEnvelope, request);
        }
    }

    private void sendOrderedEmails(ContractEnvelope contractEnvelope, HttpServletRequest request) {
        List<Signatory> signatories = contractEnvelope.getSignatories();
        signatories.sort(Comparator.comparingInt(Signatory::getSignatoryOrder));

        for(Signatory envelopeSignatory : signatories) {
            if(envelopeSignatory.isSigned()) continue;
            else if (envelopeSignatory.getType().equals(SignatoryType.RECIEVES_A_COPY)){
                envelopeSignatory.setSigned(true);
                signatoryRepository.save(envelopeSignatory);
                sendEmail(contractEnvelope, envelopeSignatory, request);
            } else if(envelopeSignatory.getType().equals(SignatoryType.NEEDS_TO_SIGN) || envelopeSignatory.getType().equals(SignatoryType.DELEGATED)){
                sendEmail(contractEnvelope, envelopeSignatory, request);
                break;
            }
        }
    }

    @SneakyThrows
    private void sendEmail(ContractEnvelope contractEnvelope, Signatory signatory, HttpServletRequest request) {
        System.out.println("Sending ordered invitation email to " + signatory.getName());
    }

    private boolean checkIfAllSignatoriesHasSigned(ContractEnvelope envelope){
        List<Signatory> signatories = envelope.getSignatories();
        return signatories.stream().allMatch(Signatory::isSigned);
    }
}
