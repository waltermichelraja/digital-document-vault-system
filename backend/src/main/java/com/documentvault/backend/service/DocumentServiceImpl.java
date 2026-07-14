package com.documentvault.backend.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.dto.PageResponse;
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

    private static final Set<String> ALLOWED_SORT_FIELDS=Set.of(
            "documentTitle",
            "uploadedAt",
            "category",
            "fileSize"
    );

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
    public PageResponse<DocumentResponse> getAllDocuments(String search,String category,int page,int size,String sort){
        User currentUser=currentUserService.getCurrentUser();
        String[] sortParts=sort.split(",");
        String sortField=sortParts[0];
        if(!ALLOWED_SORT_FIELDS.contains(sortField)){
            throw new IllegalArgumentException("invalid sort field.");
        }
        Sort.Direction direction=
                sortParts.length>1 &&
                sortParts[1].equalsIgnoreCase("asc")?Sort.Direction.ASC:Sort.Direction.DESC;
        Pageable pageable=PageRequest.of(
                page,
                size,
                Sort.by(direction,sortField)
        );
        Page<Document> documents;
        boolean hasSearch=search!=null && !search.isBlank();
        boolean hasCategory=category!=null && !category.isBlank();
        if(hasSearch && hasCategory){
            documents=documentRepository
                    .findByOwnerAndDocumentTitleContainingIgnoreCaseAndCategoryIgnoreCase(
                            currentUser,
                            search,
                            category,
                            pageable
                    );
        }else if(hasSearch){
            documents=documentRepository
                    .findByOwnerAndDocumentTitleContainingIgnoreCase(
                            currentUser,
                            search,
                            pageable
                    );
        }else if(hasCategory){
            documents=documentRepository
                    .findByOwnerAndCategoryIgnoreCase(
                            currentUser,
                            category,
                            pageable
                    );
        }else{
            documents=documentRepository.findByOwner(
                    currentUser,
                    pageable
            );
        }
        return new PageResponse<>(
                documents.getContent().stream().map(this::mapToDocumentResponse).collect(Collectors.toList()),
                documents.getNumber(),
                documents.getSize(),
                documents.getTotalElements(),
                documents.getTotalPages(),
                documents.isLast()
        );
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
