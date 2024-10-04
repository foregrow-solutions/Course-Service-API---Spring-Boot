package com.winggs.course.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    String upload(MultipartFile file) throws IOException;
    void remove(String id) throws IOException;
}
