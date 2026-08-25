package com.documentvault.backend.auth.dto;

import com.documentvault.backend.entity.Role;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse{
    private boolean success;
    private String message;
    private String token;
    private String fullName;
    private Role role;

    public AuthResponse(boolean success,String message){
        this.success=success;
        this.message=message;
    }

    public AuthResponse(boolean success,String message,String token){
        this.success=success;
        this.message=message;
        this.token=token;
    }
}