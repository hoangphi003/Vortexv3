package com.techvortex.vortex.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
     String storeFile(MultipartFile file);
}
