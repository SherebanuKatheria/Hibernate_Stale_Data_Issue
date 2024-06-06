package com.example.StaleDataIssue.esignEmailState.states;

import com.example.StaleDataIssue.esignEmailState.EsignEmailState;

public class EndState implements EsignEmailState {
    @Override
    public EsignEmailState nextState() {
        return null;
    }
}
