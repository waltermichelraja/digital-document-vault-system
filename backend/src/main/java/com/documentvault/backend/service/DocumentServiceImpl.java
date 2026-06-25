package com.documentvault.backend.service;

import com.documentvault.backend.dto.UploadResponse;
import com.documentvault.backend.entity.Document;
import com.documentvault.backend.repository.DocumentRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceImpl implements DocumentService{
    private final DocumentRepository documentRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository){
        this.documentRepository=documentRepository;
    }

    @Override
    public UploadResponse uploadDocument(MultipartFile file){

        // implementation...

        return null;
    }
}