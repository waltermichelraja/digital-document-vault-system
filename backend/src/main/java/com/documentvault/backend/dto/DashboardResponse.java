package com.documentvault.backend.dto;

public class DashboardResponse{
    private long totalUsers;
    private long totalAdmins;
    private long totalManagers;
    private long totalEmployees;
    private long totalDocuments;

    public DashboardResponse(){}

    public DashboardResponse(
            long totalUsers,
            long totalAdmins,
            long totalManagers,
            long totalEmployees,
            long totalDocuments){

        this.totalUsers=totalUsers;
        this.totalAdmins=totalAdmins;
        this.totalManagers=totalManagers;
        this.totalEmployees=totalEmployees;
        this.totalDocuments=totalDocuments;
    }

    public long getTotalUsers(){
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers){
        this.totalUsers=totalUsers;
    }

    public long getTotalAdmins(){
        return totalAdmins;
    }

    public void setTotalAdmins(long totalAdmins){
        this.totalAdmins=totalAdmins;
    }

    public long getTotalManagers(){
        return totalManagers;
    }

    public void setTotalManagers(long totalManagers){
        this.totalManagers=totalManagers;
    }

    public long getTotalEmployees(){
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees){
        this.totalEmployees=totalEmployees;
    }

    public long getTotalDocuments(){
        return totalDocuments;
    }

    public void setTotalDocuments(long totalDocuments){
        this.totalDocuments=totalDocuments;
    }
}