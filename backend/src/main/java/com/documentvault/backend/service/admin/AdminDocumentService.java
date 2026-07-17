package com.documentvault.backend.service.admin;

import org.springframework.core.io.Resource;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.dto.PageResponse;

public interface AdminDocumentService{
    PageResponse<DocumentResponse> getAllDocuments(String search,String category,int page,int size,String sort);

    Resource downloadDocument(Long documentId);

    void deleteDocument(Long documentId);
}