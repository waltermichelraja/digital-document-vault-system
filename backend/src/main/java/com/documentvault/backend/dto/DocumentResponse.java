package com.documentvault.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponse{
    private Long id;
    private String documentTitle;
    private String category;
    private String originalFileName;
    private String contentType;
    private Long fileSize;
    private LocalDateTime uploadedAt;
}