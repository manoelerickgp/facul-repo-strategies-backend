package com.tech.students.service;

import com.tech.students.config.FileStorageProperties;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    public FileService(FileStorageProperties fileStorageProperties) {
        fileStorageProperties.getUploadDir();
    }
}
