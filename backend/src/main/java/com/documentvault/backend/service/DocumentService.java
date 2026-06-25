package com.documentvault.backend.service;

import com.documentvault.backend.dto.UploadResponse;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    UploadResponse uploadDocument(MultipartFile file);
}