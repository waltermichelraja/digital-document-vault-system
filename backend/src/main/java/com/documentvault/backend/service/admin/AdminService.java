package com.documentvault.backend.service.admin;

import java.util.List;

import com.documentvault.backend.dto.UserResponse;

public interface AdminService{
    List<UserResponse> getAllUsers();
}