package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AllSignatorySigned implements EsignEmailState {

    private final EsignEmailStateFactory emailStateFactory;

    @Override
    public EsignEmailState nextState() {
        return emailStateFactory.createState("EndState");
    }
}
