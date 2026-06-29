package com.documentvault.backend.dto;

import java.time.LocalDateTime;

public class DocumentResponse{
    private Long id;
    private String documentTitle;
    private String category;
    private String originalFileName;
    private String contentType;
    private Long fileSize;
    private LocalDateTime uploadedAt;

    public DocumentResponse(){}

    public DocumentResponse(Long id,String documentTitle,String category,String originalFileName,String contentType,Long fileSize,LocalDateTime uploadedAt){
        this.id=id;
        this.documentTitle=documentTitle;
        this.category=category;
        this.originalFileName=originalFileName;
        this.contentType=contentType;
        this.fileSize=fileSize;
        this.uploadedAt=uploadedAt;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id=id;
    }

    public String getDocumentTitle(){
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle){
        this.documentTitle=documentTitle;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category=category;
    }

    public String getOriginalFileName(){
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName){
        this.originalFileName=originalFileName;
    }

    public String getContentType(){
        return contentType;
    }

    public void setContentType(String contentType){
        this.contentType=contentType;
    }

    public Long getFileSize(){
        return fileSize;
    }

    public void setFileSize(Long fileSize){
        this.fileSize=fileSize;
    }

    public LocalDateTime getUploadedAt(){
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt){
        this.uploadedAt=uploadedAt;
    }
}