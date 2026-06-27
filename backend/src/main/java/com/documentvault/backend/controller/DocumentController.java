package com.documentvault.backend.controller;

import com.documentvault.backend.dto.UploadResponse;
import com.documentvault.backend.service.DocumentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentController{
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService){
        this.documentService=documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadDocument(@RequestParam("file") MultipartFile file){
        UploadResponse response=documentService.uploadDocument(file);
        return ResponseEntity.ok(response);
    }
}