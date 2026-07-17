package com.documentvault.backend.mapper;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.entity.Document;

public final class DocumentMapper{
    private DocumentMapper(){}

    public static DocumentResponse toResponse(Document document){
        return new DocumentResponse(
                document.getId(),
                document.getDocumentTitle(),
                document.getCategory(),
                document.getOriginalFileName(),
                document.getContentType(),
                document.getFileSize(),
                document.getUploadedAt()
        );
    }
}