package com.documentvault.backend.service.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.documentvault.backend.constant.StorageConstants;
import com.documentvault.backend.exception.StorageException;

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
            throw new StorageException("unable to store file.",e);
        }
    }

    @Override
    public Resource load(String storedFileName){
        try{
            Path file=Paths.get(StorageConstants.DOCUMENT_DIRECTORY)
                    .resolve(storedFileName);
            Resource resource=new UrlResource(file.toUri());
            if(resource.exists() && resource.isReadable()){
                return resource;
            }
            throw new StorageException("unable to read file.");
        }catch(MalformedURLException e){
            throw new StorageException("unable to read file.",e);
        }
    }

    @Override
    public void delete(String storedFileName){
        try{
            Path file=Paths.get(StorageConstants.DOCUMENT_DIRECTORY)
                    .resolve(storedFileName);
            Files.deleteIfExists(file);
        }catch(IOException e){
            throw new StorageException("unable to delete file.",e);
        }
    }

}
