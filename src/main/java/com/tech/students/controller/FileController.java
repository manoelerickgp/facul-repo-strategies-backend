package com.tech.students.controller;

import com.tech.students.domain.File;
import com.tech.students.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/v1/files")
public class FileController {

    private FileService fileService;

    @PostMapping(value = "/upload")
    public File uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileService.saveFile(file);

        String filePath = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/downloadFile")
                .path(fileName)
                .toUriString();
        return new File(fileName, filePath, file.getContentType(), file.getSize());
    }

    @GetMapping(value = "/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {

        Resource resource = fileService.loadFile(fileName);
        String contentType = fileService.getContentType(request, resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
