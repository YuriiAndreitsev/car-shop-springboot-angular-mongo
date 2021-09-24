package com.bezkoder.springjwt.services;

import com.bezkoder.springjwt.config.FileStorageProperties;
import com.bezkoder.springjwt.exception.FileStorageException;
import com.bezkoder.springjwt.exception.MyFileNotFoundException;
import com.bezkoder.springjwt.utils.ImageUrlBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@Service
@Slf4j
public class FileStorageService {
    private final Path fileStorageLocation;
    @Autowired
    ImageUrlBuilder imageBuilder;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
//        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
//                .toAbsolutePath().normalize(); //If you need specific location on a disk
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).normalize();
        log.warn("Path to upload = {}", fileStorageLocation.toAbsolutePath());
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String model, String brand) {
        String fileName = imageBuilder.createFileName(model, brand);
        log.warn("Path to upload = {}", fileStorageLocation);
        try {
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetPath = Paths.get(imageBuilder.resolveFilePath(model, brand)).normalize();
            Files.createDirectories(targetPath);
//            Path targetLocation =  fileStorageLocation.resolve(fileName);
            Path targetLocation = targetPath.resolve(fileName);
            log.warn("targetLocation to upload = {}", targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        log.warn("Path to upload = {}", fileStorageLocation);
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeFile(MultipartFile file, String filePath) {
        Path path;
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        log.warn("Path to upload = {}", fileStorageLocation);
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            path = Paths.get(filePath).normalize();
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = path.resolve(fileName);
            log.warn("Target Location : '{}'", targetLocation.toAbsolutePath());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get("./src/main/resources/images/tesla/model-s").normalize();
//            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public ByteArrayOutputStream downloadFile(String keyName) {
        try {
            InputStream is = new FileInputStream(new File("./src/main/resources/images/tesla/model-s/model-s-tesla-1.jpg"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[4096];
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream;
        } catch (IOException ioException) {
            log.error("IOException: " + ioException.getMessage());
        }
        return null;
    }
}
