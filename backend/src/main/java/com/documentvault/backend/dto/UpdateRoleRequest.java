package com.documentvault.backend.dto;

import jakarta.validation.constraints.NotNull;

import com.documentvault.backend.entity.Role;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class UpdateRoleRequest{
    @NotNull(message="role cannot be null.")
    private Role role;
}