package com.documentvault.backend.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService{

    String store(MultipartFile file);

    byte[] read(String storedFileName);

    void delete(String storedFileName);

}