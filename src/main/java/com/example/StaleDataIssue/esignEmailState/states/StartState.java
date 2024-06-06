package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.esignEmailState.EsignEmailState;
import com.example.StaleDataIssue.esignEmailState.EsignEmailStateFactory;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class StartState implements EsignEmailState {
    private final EsignEmailStateFactory emailStateFactory;

    @Override
    @SneakyThrows
    public EsignEmailState nextState() {
            return emailStateFactory.createState("UnorderedEmailState");
    }
}