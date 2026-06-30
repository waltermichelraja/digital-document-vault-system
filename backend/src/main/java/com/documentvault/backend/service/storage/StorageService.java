package com.documentvault.backend.service.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService{
    String store(MultipartFile file);

    Resource load(String storedFileName);

    void delete(String storedFileName);

}
