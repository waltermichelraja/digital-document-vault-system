package com.documentvault.backend.service.manager;

import org.springframework.core.io.Resource;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.dto.PageResponse;

public interface ManagerDocumentService {
    PageResponse<DocumentResponse> getAllDocuments(String search,String category,int page,int size,String sort);

    Resource downloadDocument(Long documentId);
}