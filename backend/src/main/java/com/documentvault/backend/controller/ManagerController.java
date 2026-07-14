package com.documentvault.backend.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.dto.PageResponse;
import com.documentvault.backend.service.manager.ManagerService;

@RestController
@RequestMapping("/api/manager")
@PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
public class ManagerController{
    private final ManagerService managerService;

    public ManagerController(ManagerService managerService){
        this.managerService=managerService;
    }

    @GetMapping("/documents")
    public ResponseEntity<PageResponse<DocumentResponse>> getAllDocuments(
            @RequestParam(required=false) String search,
            @RequestParam(required=false) String category,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size,
            @RequestParam(defaultValue="uploadedAt,desc") String sort){
        return ResponseEntity.ok(
                managerService.getAllDocuments(search,category,page,size,sort)
        );
    }

    @GetMapping("/documents/download/{id}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id){
        Resource resource=managerService.downloadDocument(id);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+resource.getFilename()+"\""
                )
                .body(resource);
    }
}