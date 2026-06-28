package com.documentvault.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.documentvault.backend.dto.UploadResponse;
import com.documentvault.backend.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController{
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService){
        this.documentService=documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadDocument(
            @RequestParam("documentTitle") String documentTitle,
            @RequestParam("category") String category,
            @RequestParam("file") MultipartFile file){

        UploadResponse response=documentService.uploadDocument(documentTitle,category,file);
        return ResponseEntity.ok(response);
    }
}