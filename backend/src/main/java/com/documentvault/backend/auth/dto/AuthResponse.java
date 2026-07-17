package com.documentvault.backend.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse{
    private boolean success;
    private String message;
    private String token;

    public AuthResponse(boolean success,String message){
        this.success=success;
        this.message=message;
    }
}