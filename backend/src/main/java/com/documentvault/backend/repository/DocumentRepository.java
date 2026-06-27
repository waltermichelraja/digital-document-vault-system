package com.documentvault.backend.repository;

import com.documentvault.backend.entity.Document;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long>{
    
}