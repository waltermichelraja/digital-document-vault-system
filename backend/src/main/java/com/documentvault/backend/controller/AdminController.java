package com.documentvault.backend.controller;

import java.util.List;

import com.documentvault.backend.dto.*;
import jakarta.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.documentvault.backend.service.admin.AdminDocumentService;
import com.documentvault.backend.service.admin.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController{
    private final AdminService adminService;
    private final AdminDocumentService adminDocumentService;

    public AdminController(AdminService adminService,AdminDocumentService adminDocumentService){
        this.adminService=adminService;
        this.adminDocumentService=adminDocumentService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(){
        return adminService.getAllUsers();
    }

    @PatchMapping("/users/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateRole(@PathVariable String id,@Valid @RequestBody UpdateRoleRequest request){
        return ResponseEntity.ok(
                adminService.updateUserRole(id,request)
        );
    }

    @GetMapping("/documents")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageResponse<DocumentResponse>> getAllDocuments(
            @RequestParam(required=false) String search,
            @RequestParam(required=false) String category,
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="10") int size,
            @RequestParam(defaultValue="uploadedAt,desc") String sort){
        return ResponseEntity.ok(
                adminDocumentService.getAllDocuments(search,category,page,size, sort)
        );
    }

    @GetMapping("/documents/download/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id){
        Resource resource=adminDocumentService.downloadDocument(id);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+resource.getFilename()+"\""
                )
                .body(resource);
    }

    @DeleteMapping("/documents/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id){
        adminDocumentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardResponse> getDashboard(){
        return ResponseEntity.ok(
                adminService.getDashboard()
        );
    }
}