package com.documentvault.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="documents")
public class Document{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String fileName;

    @Column(nullable=false,unique=true)
    private String storedFileName;

    @Column(nullable=false)
    private String contentType;

    @Column(nullable=false)
    private Long fileSize;

    @Column(nullable=false)
    private String filePath;

    @Column(nullable=false,updatable=false)
    private LocalDateTime uploadedAt;

    public Document(){}

    @PrePersist
    public void prePersist(){
        uploadedAt=LocalDateTime.now();
    }
    public LocalDateTime getUploadedAt(){
        return uploadedAt;
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
}