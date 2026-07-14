package com.documentvault.backend.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UploadDocumentRequest{
    @NotBlank(message="document title cannot be empty.")
    private String documentTitle;

    @NotBlank(message="category cannot be empty.")
    private String category;

    @NotNull(message="file cannot be empty.")
    private MultipartFile file;
}