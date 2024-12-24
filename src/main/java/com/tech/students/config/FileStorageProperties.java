package com.tech.students.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileStorageProperties {

    @Value("${file.uploadDir}")
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }
}
