package com.tech.students.service;

import com.tech.students.config.FileStorageProperties;
import com.tech.students.exception.UploadFileException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final Path fileStorageLocation;

    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new UploadFileException("Algo deu errado ao tentar criar a pasta", e);
        }
    }

    public String getContentType(HttpServletRequest request, Resource resource) {
        String contentType =  null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException e) {
            throw new RuntimeException("Não foi possível determinar o tipo de arquivo");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }
}
