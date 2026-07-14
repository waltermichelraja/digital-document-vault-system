package com.documentvault.backend.auth.service;

import com.documentvault.backend.auth.dto.AuthResponse;
import com.documentvault.backend.auth.dto.RegisterRequest;

public interface AuthService{
    AuthResponse register(RegisterRequest request);

}