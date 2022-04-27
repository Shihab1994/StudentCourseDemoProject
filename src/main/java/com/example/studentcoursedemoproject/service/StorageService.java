package com.example.studentcoursedemoproject.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();
    void store(String fileName, MultipartFile file);
    String getFileUrl();
    Stream<Path> loadAll();
    Path load(String filename);
    Resource loadAsResource(String filename);
    void deleteAll();

}
