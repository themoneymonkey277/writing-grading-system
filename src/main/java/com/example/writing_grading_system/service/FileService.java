package com.example.writing_grading_system.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile file) throws IOException;

    byte[] downloadFileFromUrl(String fileUrl) throws IOException;
}
