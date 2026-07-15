package com.documentvault.backend.controller;

import com.documentvault.backend.dto.UploadDocumentRequest;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.dto.PageResponse;
import com.documentvault.backend.dto.UploadResponse;
import com.documentvault.backend.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
public class DocumentController{
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService){
        this.documentService=documentService;
    }

    @PostMapping(value="/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> uploadDocument(@Valid @ModelAttribute UploadDocumentRequest request){
        if(request.getFile().isEmpty()){
            throw new IllegalArgumentException("file cannot be empty.");
        }
        UploadResponse response=documentService.uploadDocument(request.getDocumentTitle(),request.getCategory(),request.getFile());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<DocumentResponse>> getAllDocuments(
            @RequestParam(required=false) String search,
            @RequestParam(required=false) String category,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size,
            @RequestParam(defaultValue="uploadedAt,desc") String sort){
        return ResponseEntity.ok(documentService.getAllDocuments(search,category,page,size,sort));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id){
        Resource resource=documentService.downloadDocument(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+resource.getFilename()+"\""
                ).body(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id){
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
