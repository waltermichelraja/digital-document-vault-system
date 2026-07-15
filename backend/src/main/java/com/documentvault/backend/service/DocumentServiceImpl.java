package com.documentvault.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.dto.UploadResponse;
import com.documentvault.backend.entity.Document;
import com.documentvault.backend.entity.User;
import com.documentvault.backend.exception.AccessDeniedException;
import com.documentvault.backend.exception.DocumentNotFoundException;
import com.documentvault.backend.repository.DocumentRepository;
import com.documentvault.backend.security.currentuser.CurrentUserService;
import com.documentvault.backend.service.storage.StorageService;

@Service
public class DocumentServiceImpl implements DocumentService{
    private final DocumentRepository documentRepository;
    private final StorageService storageService;
    private final CurrentUserService currentUserService;

    public DocumentServiceImpl(DocumentRepository documentRepository,StorageService storageService,CurrentUserService currentUserService){
        this.documentRepository=documentRepository;
        this.storageService=storageService;
        this.currentUserService=currentUserService;
    }

    @Override
    public UploadResponse uploadDocument(String documentTitle,String category,MultipartFile file){
        String storedFileName=storageService.store(file);
        Document document=new Document();
        User currentUser=currentUserService.getCurrentUser();

        document.setDocumentTitle(documentTitle);
        document.setCategory(category);

        document.setOriginalFileName(file.getOriginalFilename());
        document.setStoredFileName(storedFileName);

        document.setContentType(file.getContentType());
        document.setFileSize(file.getSize());

        document.setFilePath("storage/documents/"+storedFileName);

        document.setOwner(currentUser);
        documentRepository.save(document);
        return new UploadResponse(
                true,
                "file uploaded successfully.",
                document.getId()
        );
    }

    @Override
    public List<DocumentResponse> getAllDocuments(){
        User currentUser=currentUserService.getCurrentUser();
        return documentRepository.findByOwner(currentUser)
                .stream()
                .map(this::mapToDocumentResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Resource downloadDocument(Long documentId){
        Document document=validateOwnership(documentId);
        return storageService.load(document.getStoredFileName());
    }

    @Override
    public void deleteDocument(Long documentId){
        Document document=validateOwnership(documentId);
        storageService.delete(document.getStoredFileName());
        documentRepository.delete(document);
    }

    private DocumentResponse mapToDocumentResponse(Document document){
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

    private Document validateOwnership(Long documentId){
        User currentUser=currentUserService.getCurrentUser();
        Document document=documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new DocumentNotFoundException("document not found."));
        if(document.getOwner()==null || !document.getOwner().getId().equals(currentUser.getId())){
            throw new AccessDeniedException("access denied.");
        }
        return document;
    }
}
