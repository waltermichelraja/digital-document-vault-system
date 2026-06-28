package com.documentvault.backend.service;

import org.springframework.web.multipart.MultipartFile;

import com.documentvault.backend.dto.UploadResponse;

public interface DocumentService{
    UploadResponse uploadDocument(String documentTitle,String category,MultipartFile file);
}