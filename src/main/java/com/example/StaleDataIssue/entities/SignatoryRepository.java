package com.example.StaleDataIssue.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatoryRepository extends JpaRepository<Signatory, String> {
}
