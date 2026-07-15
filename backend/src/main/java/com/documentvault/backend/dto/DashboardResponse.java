package com.documentvault.backend.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse{
    private long totalUsers;
    private long totalAdmins;
    private long totalManagers;
    private long totalEmployees;
    private long totalDocuments;
}