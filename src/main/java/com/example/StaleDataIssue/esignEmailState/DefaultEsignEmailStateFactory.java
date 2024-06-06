package com.example.StaleDataIssue.esignEmailState;

import com.example.StaleDataIssue.entities.ContractEnvelopeRepository;
import com.example.StaleDataIssue.esignEmailState.states.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultEsignEmailStateFactory implements EsignEmailStateFactory {

    private final ContractEnvelopeRepository contractEnvelopeRepository;

    @Override
    public EsignEmailState createState(String stateName) {
        return switch (stateName) {
            case "StartState" -> new StartState(this);
            case "UnorderedEmailState" -> new UnorderedEmailState(this, contractEnvelopeRepository);
            case "AllSignatorySigned" -> new AllSignatorySigned(this);
            case "UnorderedDelegatedSignatory" -> new UnorderedDelegatedSignatory(this);
            case "EndState" -> new EndState();
            default -> null;
        };
    }
}
