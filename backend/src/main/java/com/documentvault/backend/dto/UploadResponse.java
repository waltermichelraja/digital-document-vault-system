package com.documentvault.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse{
    private boolean success;
    private String message;
    private Long documentId;
}