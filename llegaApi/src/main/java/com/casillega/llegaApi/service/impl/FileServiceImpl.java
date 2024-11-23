package com.casillega.llegaApi.service.impl;

import com.casillega.llegaApi.entities.EventMedia;
import com.casillega.llegaApi.repositories.EventMediaRepository;
import com.casillega.llegaApi.service.interfac.EventMediaService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements EventMediaService{
    private final EventMediaRepository eventMediaRepository;

    public FileServiceImpl(EventMediaRepository eventMediaRepository) {
        this.eventMediaRepository = eventMediaRepository;
    }

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //getFile name
        String fileName = file.getOriginalFilename();
        //getting the file path
        String filePath = path + File.separator + fileName;
        // creating file object
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
        //copy or upload the file to the path
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }

    @Override
    public InputStream getSource(String path, String filename) throws FileNotFoundException {

        //getting the path
        String filePath = path + File.separator + filename;
        
        return new FileInputStream(filePath);
    }

    @Override
    public EventMedia putEventMedia(EventMedia eventMedia, MultipartFile file) throws IOException {
        return null;
    }

    @Override
    public EventMedia getEventMedia(String path, String filename) {
        return null;
    }
}
