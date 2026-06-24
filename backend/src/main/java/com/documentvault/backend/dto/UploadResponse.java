package com.documentvault.backend.dto;

public class UploadResponse{
    private String message;
    private Long documentId;

    public UploadResponse(){}

    public UploadResponse(String message,Long documentId){
        this.message=message;
        this.documentId=documentId;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message=message;
    }

    public Long getDocumentId(){
        return documentId;
    }
    public void setDocumentId(Long documentId){
        this.documentId=documentId;
    }
}