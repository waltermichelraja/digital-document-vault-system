package com.documentvault.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="documents")
public class Document{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String storedFileName;
    private String contentType;
    private Long fileSize;
    private String filePath;
    private LocalDateTime uploadedAt;

    public Document(){}

    @PrePersist
    public void prePersist(){
        uploadedAt=LocalDateTime.now();
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

    public String getStoredFileName(){
        return storedFileName;
    }
    public void setStoredFileName(String storedFileName){
        this.storedFileName=storedFileName;
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

    public String getFilePath(){
        return filePath;
    }
    public void setFilePath(String filePath){
        this.filePath=filePath;
    }

    public LocalDateTime getUploadedAt(){
        return uploadedAt;
    }
}