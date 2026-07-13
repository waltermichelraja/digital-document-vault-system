package com.documentvault.backend.controller;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.documentvault.backend.dto.DocumentResponse;
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
    public ResponseEntity<UploadResponse> uploadDocument(@RequestParam String documentTitle,@RequestParam String category,@RequestParam MultipartFile file){
        if(documentTitle==null||documentTitle.isBlank()){
            throw new IllegalArgumentException("document title cannot be empty.");
        }
        if(category==null||category.isBlank()){
            throw new IllegalArgumentException("category cannot be empty.");
        }
        if(file==null||file.isEmpty()){
            throw new IllegalArgumentException("file cannot be empty.");
        }
        UploadResponse response=documentService.uploadDocument(documentTitle,category,file);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getAllDocuments(){
        return ResponseEntity.ok(
                documentService.getAllDocuments()
        );
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id){
        Resource resource=documentService.downloadDocument(id);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+resource.getFilename()+"\""
                )
                .body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id){
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
