package com.example.StaleDataIssue.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "contract_envelope")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractEnvelope {
    @Id
    @Column(name = "contract_envelope_id")
    private String contractEnvelopId;

    @ManyToMany
    @JoinTable(
            name = "contract_envelope_signatories",
            joinColumns = @JoinColumn(name = "contract_envelope_id"),
            inverseJoinColumns = @JoinColumn(name = "signatory_id")
    )
    @Nullable
    private List<Signatory> signatories;
}