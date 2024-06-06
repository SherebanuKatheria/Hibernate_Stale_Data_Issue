package com.example.StaleDataIssue.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "signatory")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Signatory {
    @Id
    @Column(name = "signatory_id")
    private String signatoryId;

    private String name;

    private boolean isSigned;

    private String type;
}