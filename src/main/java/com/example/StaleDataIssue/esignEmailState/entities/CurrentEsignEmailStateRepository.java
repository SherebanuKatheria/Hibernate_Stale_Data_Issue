package com.example.StaleDataIssue.esignEmailState.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentEsignEmailStateRepository extends JpaRepository<CurrentEsignEmailState, Integer> {

    @Query(value = "SELECT * from current_envelope_email_state where envelope_id = :envelopeId", nativeQuery = true)
    CurrentEsignEmailState findByEnvelopeId(@Param("envelopeId") String contractEnvelopeId);

    @Modifying
    @Query(value = "update current_envelope_email_state set current_state = :state where envelope_id = :envelopeId", nativeQuery = true)
    void updateCurrentState(@Param("envelopeId") String contractEnvelopeId,@Param("state") String state);

    boolean existsByEnvelopeId(String contractEnvelopeId);
}
