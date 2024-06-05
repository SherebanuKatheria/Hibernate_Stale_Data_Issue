package com.example.StaleDataIssue.esignEmailState.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "current_envelope_email_state")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentEsignEmailState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String envelopeId;

    private String currentState;

    @PrePersist
    public void prePersist(){
        this.currentState = "StartState";
    }//Set message and body here
}
