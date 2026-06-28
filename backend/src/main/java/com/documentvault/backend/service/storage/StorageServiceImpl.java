package com.documentvault.backend.service.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.documentvault.backend.constant.StorageConstants;

@Service
public class StorageServiceImpl implements StorageService{
    @Override
    public String store(MultipartFile file){
        try{
            Path storageDirectory=Paths.get(StorageConstants.DOCUMENT_DIRECTORY);
            if(!Files.exists(storageDirectory)){
                Files.createDirectories(storageDirectory);
            }
            String storedFileName=UUID.randomUUID()+"_"+file.getOriginalFilename();
            Path destination=storageDirectory.resolve(storedFileName);
            Files.copy(
                    file.getInputStream(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );
            return storedFileName;

        }catch(IOException e){
            throw new RuntimeException("unable to store file.",e);
        }
    }

    @Override
    public byte[] read(String storedFileName){
        try{
            Path file=Paths.get(StorageConstants.DOCUMENT_DIRECTORY).resolve(storedFileName);
            return Files.readAllBytes(file);
        }catch (IOException e){
            throw new RuntimeException("unable to read file.",e);
        }
    }

    @Override
    public void delete(String storedFileName){
        try{
            Path file=Paths.get(StorageConstants.DOCUMENT_DIRECTORY).resolve(storedFileName);
            Files.deleteIfExists(file);
        }catch (IOException e){
            throw new RuntimeException("unable to delete file.",e);
        }
    }
}