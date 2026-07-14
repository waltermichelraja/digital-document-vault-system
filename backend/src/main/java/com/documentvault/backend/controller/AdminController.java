package com.documentvault.backend.controller;

import java.util.List;

import com.documentvault.backend.dto.UpdateRoleRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.documentvault.backend.dto.UserResponse;
import com.documentvault.backend.service.admin.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController{
    private final AdminService adminService;

    public AdminController(AdminService adminService){
        this.adminService=adminService;
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
}