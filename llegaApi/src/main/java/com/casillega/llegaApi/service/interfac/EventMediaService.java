package com.casillega.llegaApi.service.interfac;

import com.casillega.llegaApi.entities.EventMedia;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface EventMediaService {

    String uploadFile(String path, MultipartFile file) throws IOException;

    InputStream getSource(String path, String filename) throws FileNotFoundException;
    EventMedia putEventMedia(EventMedia eventMedia, MultipartFile file) throws IOException;
    EventMedia getEventMedia(String path, String filename);

}
