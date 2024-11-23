package com.casillega.llegaApi.controllers;

import com.casillega.llegaApi.service.interfac.EventMediaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/file/")
public class FileController {
    private  EventMediaService file_service;
    public FileController(EventMediaService file_service) {
        this.file_service = file_service;
    }
    @Value("${project.poster}")
    private String path;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFileHandler(@RequestPart MultipartFile file) throws IOException {
       String uploadedFilename = file_service.uploadFile(path, file);
        return ResponseEntity.ok("File uploaded successfully" + uploadedFilename);
    }

    @GetMapping("/{fileName}")
    public void serverFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        InputStream resourceFile = file_service.getSource(path, fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile, response.getOutputStream());

    }
}
