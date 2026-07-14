package com.documentvault.backend.dto;

import com.documentvault.backend.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse{
    private String id;
    private String fullName;
    private String email;
    private Role role;
}