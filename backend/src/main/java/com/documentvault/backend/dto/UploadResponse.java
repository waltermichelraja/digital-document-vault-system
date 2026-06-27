package com.documentvault.backend.dto;

public class UploadResponse{
    private boolean success;
    private String message;
    private Long documentId;

    public UploadResponse(){}

    public UploadResponse(boolean success,String message,Long documentId){
        this.success=success;
        this.message=message;
        this.documentId=documentId;
    }

    public boolean isSuccess(){
        return success;
    }
    public void setSuccess(boolean success){
        this.success=success;
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