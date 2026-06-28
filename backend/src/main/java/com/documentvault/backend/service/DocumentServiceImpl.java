package com.documentvault.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.documentvault.backend.dto.UploadResponse;
import com.documentvault.backend.entity.Document;
import com.documentvault.backend.repository.DocumentRepository;
import com.documentvault.backend.service.storage.StorageService;

@Service
public class DocumentServiceImpl implements DocumentService{
    private final DocumentRepository documentRepository;
    private final StorageService storageService;

    public DocumentServiceImpl(DocumentRepository documentRepository,StorageService storageService){
        this.documentRepository=documentRepository;
        this.storageService=storageService;
    }

    @Override
    public UploadResponse uploadDocument(String documentTitle,String category,MultipartFile file){
        String storedFileName=storageService.store(file);
        Document document=new Document();

        document.setDocumentTitle(documentTitle);
        document.setCategory(category);

        document.setOriginalFileName(file.getOriginalFilename());
        document.setStoredFileName(storedFileName);

        document.setContentType(file.getContentType());
        document.setFileSize(file.getSize());

        document.setFilePath("storage/documents/"+storedFileName);

        documentRepository.save(document);

        return new UploadResponse(
                true,
                "file uploaded successfully.",
                document.getId()
        );
    }
}