package com.documentvault.backend.dto;

import java.time.LocalDateTime;

public class DocumentResponse{
    private Long id;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private LocalDateTime uploadedAt;

    public DocumentResponse(){}

    public DocumentResponse(Long id,String fileName,String contentType,Long fileSize,LocalDateTime uploadedAt){
        this.id=id;
        this.fileName=fileName;
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

    public String getFileName(){
        return fileName;
    }
    public void setFileName(String fileName){
        this.fileName=fileName;
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