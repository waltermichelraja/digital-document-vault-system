package com.documentvault.backend.dto;

import jakarta.validation.constraints.NotNull;

import com.documentvault.backend.entity.Role;

public class UpdateRoleRequest{
    @NotNull(message="role cannot be null.")
    private Role role;

    public UpdateRoleRequest(){}

    public Role getRole(){
        return role;
    }

    public void setRole(Role role){
        this.role=role;
    }
}