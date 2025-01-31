package com.example.zadanie_6.repository;

import com.example.zadanie_6.model.Communication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationRepository extends JpaRepository<Communication, Long> {
    Communication findByName(String name);
    List<Communication> findByIsProcessed(boolean isProcessed);
}
