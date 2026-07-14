package com.documentvault.backend.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.dto.PageResponse;
import com.documentvault.backend.dto.UploadResponse;

public interface DocumentService{
    UploadResponse uploadDocument(String documentTitle,String category,MultipartFile file);

    PageResponse<DocumentResponse> getAllDocuments(String search,String category,int page,int size);

    Resource downloadDocument(Long documentId);

    void deleteDocument(Long documentId);
}