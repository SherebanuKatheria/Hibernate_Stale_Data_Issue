package com.example.StaleDataIssue.esignEmailState;

import com.example.StaleDataIssue.entities.ContractEnvelopeRepository;
import com.example.StaleDataIssue.entities.SignatoryRepository;
import com.example.StaleDataIssue.esignEmailState.states.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultEsignEmailStateFactory implements EsignEmailStateFactory {

    private final SignatoryRepository signatoryRepository;
    private final ContractEnvelopeRepository contractEnvelopeRepository;

    @Override
    public EsignEmailState createState(String stateName) {
        return switch (stateName) {
            case "StartState" -> new StartState(this);
            case "OrderedEmailState" -> new OrderedEmailState(this, signatoryRepository);
            case "UnorderedEmailState" -> new UnorderedEmailState(this);
            case "AllSignatorySigned" -> new AllSignatorySigned(this, contractEnvelopeRepository);
            case "OrderedDelegatedSignatory" -> new OrderedDelegatedSignatory(this);
            case "UnorderedDelegatedSignatory" -> new UnorderedDelegatedSignatory(this);
            case "DeclinedEnvelopeState" -> new DeclinedEnvelopeState(this);
            case "EndState" -> new EndState();
            default -> null;
        };
    }
}
