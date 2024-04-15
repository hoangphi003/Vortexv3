package com.techvortex.vortex.serviceimplement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.techvortex.vortex.service.FileStorageService;

@Service
public class FileStorageServiceImp implements FileStorageService {
    private final Path fileStorageLocation;

    public FileStorageServiceImp() {
        fileStorageLocation = Paths.get(
                "D:/FPT Polytechnic/SPRING 2024/DATN/Project/vortexV2/src/main/resources/static/assets/avatarprofile/")
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Kiểm tra xem tệp tin đã tồn tại hay không
            Path targetLocation = fileStorageLocation.resolve(fileName);
            if (Files.exists(targetLocation)) {
                // Nếu tệp tin đã tồn tại, hãy xóa nó
                Files.deleteIfExists(targetLocation);
            }

            // Copy file to the target location
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
