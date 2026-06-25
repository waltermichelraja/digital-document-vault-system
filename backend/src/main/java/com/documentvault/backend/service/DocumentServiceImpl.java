package com.documentvault.backend.service;

import com.documentvault.backend.dto.UploadResponse;
import com.documentvault.backend.entity.Document;
import com.documentvault.backend.repository.DocumentRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService{
    private final DocumentRepository documentRepository;
    private static final String STORAGE_DIRECTORY="storage";

    public DocumentServiceImpl(DocumentRepository documentRepository){
        this.documentRepository=documentRepository;
    }

    @Override
    public UploadResponse uploadDocument(MultipartFile file){
        try{
            Path storagePath=Paths.get(STORAGE_DIRECTORY);
            if(!Files.exists(storagePath)){
                Files.createDirectories(storagePath);
            }
            String storedFileName=UUID.randomUUID()+"_"+file.getOriginalFilename();
            Path filePath=storagePath.resolve(storedFileName);
            Files.copy(
                file.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
            );
            Document document=new Document();
            document.setFileName(file.getOriginalFilename());
            document.setStoredFileName(storedFileName);
            document.setContentType(file.getContentType());
            document.setFileSize(file.getSize());
            document.setFilePath(filePath.toString());
            documentRepository.save(document);
            return new UploadResponse(
                true,
                "File uploaded successfully",
                document.getId()
            );
        }catch(IOException e){
            throw new RuntimeException("Failed to upload file.",e);
        }
    }
}