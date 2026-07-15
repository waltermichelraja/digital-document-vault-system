package com.documentvault.backend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
public class RegisterRequest{
    @NotBlank(message="full name cannot be empty.")
    private String fullName;

    @NotBlank(message="email cannot be empty.")
    @Email(message="invalid email format.")
    private String email;

    @NotBlank(message="password cannot be empty.")
    @Size(min=6,message="password must be at least 6 characters.")
    private String password;

    public RegisterRequest(){}

    public RegisterRequest(String fullName,String email,String password){
        this.fullName=fullName;
        this.email=email;
        this.password=password;
    }
}