package com.documentvault.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.documentvault.backend.entity.Document;
import com.documentvault.backend.entity.User;

public interface DocumentRepository extends JpaRepository<Document,Long>{
    List<Document> findByOwner(User owner);

    List<Document> findByOwnerAndDocumentTitleContainingIgnoreCase(User owner,String documentTitle);

    List<Document> findByOwnerAndCategoryIgnoreCase(User owner,String category);

    List<Document> findByOwnerAndDocumentTitleContainingIgnoreCaseAndCategoryIgnoreCase(User owner,String documentTitle,String category);
}