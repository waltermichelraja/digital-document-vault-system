package com.documentvault.backend.service.manager;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.dto.PageResponse;
import com.documentvault.backend.entity.Document;
import com.documentvault.backend.exception.DocumentNotFoundException;
import com.documentvault.backend.repository.DocumentRepository;
import com.documentvault.backend.service.storage.StorageService;

@Service
public class ManagerServiceImpl implements ManagerService{
    private final DocumentRepository documentRepository;
    private final StorageService storageService;

    private static final Set<String> ALLOWED_SORT_FIELDS=Set.of(
            "documentTitle",
            "uploadedAt",
            "category",
            "fileSize"
    );

    public ManagerServiceImpl(DocumentRepository documentRepository,StorageService storageService){
        this.documentRepository=documentRepository;
        this.storageService=storageService;
    }

    @Override
    public PageResponse<DocumentResponse> getAllDocuments(
            String search,
            String category,
            int page,
            int size,
            String sort){
        String[] sortParts=sort.split(",");
        String sortField=sortParts[0];
        if(!ALLOWED_SORT_FIELDS.contains(sortField)){
            throw new IllegalArgumentException("invalid sort field.");
        }
        if(sortParts.length>1){
            if(!sortParts[1].equalsIgnoreCase("asc")
                    && !sortParts[1].equalsIgnoreCase("desc")){
                throw new IllegalArgumentException("invalid sort direction.");
            }
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
                    .findByDocumentTitleContainingIgnoreCaseAndCategoryIgnoreCase(
                            search,
                            category,
                            pageable
                    );
        }else if(hasSearch){
            documents=documentRepository
                    .findByDocumentTitleContainingIgnoreCase(
                            search,
                            pageable
                    );
        }else if(hasCategory){
            documents=documentRepository
                    .findByCategoryIgnoreCase(
                            category,
                            pageable
                    );
        }else{
            documents=documentRepository.findAll(pageable);
        }
        return new PageResponse<>(
                documents.getContent()
                        .stream()
                        .map(this::mapToDocumentResponse)
                        .collect(Collectors.toList()),
                documents.getNumber(),
                documents.getSize(),
                documents.getTotalElements(),
                documents.getTotalPages(),
                documents.isLast()
        );
    }

    @Override
    public Resource downloadDocument(Long documentId){
        Document document=documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new DocumentNotFoundException("document not found."));
        return storageService.load(
                document.getStoredFileName()
        );
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
}