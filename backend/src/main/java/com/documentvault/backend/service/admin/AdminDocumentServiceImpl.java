package com.documentvault.backend.service.admin;

import java.util.stream.Collectors;

import com.documentvault.backend.constant.SortConstants;
import com.documentvault.backend.mapper.DocumentMapper;
import com.documentvault.backend.security.currentuser.CurrentUserService;
import com.documentvault.backend.util.PageableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.documentvault.backend.dto.DocumentResponse;
import com.documentvault.backend.dto.PageResponse;
import com.documentvault.backend.entity.Document;
import com.documentvault.backend.entity.User;
import com.documentvault.backend.exception.DocumentNotFoundException;
import com.documentvault.backend.repository.DocumentRepository;
import com.documentvault.backend.service.storage.StorageService;

@Service
public class AdminDocumentServiceImpl implements AdminDocumentService{
    private final DocumentRepository documentRepository;
    private final StorageService storageService;
    private final CurrentUserService currentUserService;
    private static final Logger logger=LoggerFactory.getLogger(AdminDocumentServiceImpl.class);

    public AdminDocumentServiceImpl(DocumentRepository documentRepository,StorageService storageService,CurrentUserService currentUserService){
        this.documentRepository=documentRepository;
        this.storageService=storageService;
        this.currentUserService=currentUserService;
    }

    @Override
    public PageResponse<DocumentResponse> getAllDocuments(String search,String category,int page,int size,String sort){
        Pageable pageable= PageableUtil.createPageable(page,size,sort,SortConstants.DOCUMENT_SORT_FIELDS);
        Page<Document> documents;
        boolean hasSearch=search!=null && !search.isBlank();
        boolean hasCategory=category!=null && !category.isBlank();
        if(hasSearch && hasCategory){
            documents=documentRepository
                    .findByDocumentTitleContainingIgnoreCaseAndCategoryIgnoreCase(search,category,pageable);
        }else if(hasSearch){
            documents=documentRepository
                    .findByDocumentTitleContainingIgnoreCase(search,pageable);
        }else if(hasCategory){
            documents=documentRepository
                    .findByCategoryIgnoreCase(category,pageable);
        }else{
            documents=documentRepository.findAll(pageable);
        }
        return new PageResponse<>(
                documents.getContent().stream().map(DocumentMapper::toResponse).collect(Collectors.toList()),
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
        User currentAdmin=currentUserService.getCurrentUser();
        logger.info("admin '{}' downloaded document '{}'.",currentAdmin.getEmail(),document.getDocumentTitle());
        return storageService.load(document.getStoredFileName());
    }

    @Override
    public void deleteDocument(Long documentId){
        Document document=documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new DocumentNotFoundException("document not found."));
        storageService.delete(document.getStoredFileName());
        User currentAdmin=currentUserService.getCurrentUser();
        logger.info("admin '{}' deleted document '{}'.",currentAdmin.getEmail(),document.getDocumentTitle());
        documentRepository.delete(document);
    }
}