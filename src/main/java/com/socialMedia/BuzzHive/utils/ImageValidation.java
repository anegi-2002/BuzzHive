package com.socialMedia.BuzzHive.utils;

import com.socialMedia.BuzzHive.exception.ImageUploadException;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class ImageValidation {
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB in bytes
    private static final Set<String> ALLOWED_FILE_EXTENSIONS = Set.of("png", "jpg", "jpeg", "gif", "svg", "heic", "webp");

    public static void validateFile(MultipartFile file) throws ImageUploadException {
        String filename = file.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            throw new ImageUploadException("Invalid file name");
        }

        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_FILE_EXTENSIONS.contains(extension)) {
            throw new ImageUploadException("Unsupported file extension: " + extension);
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ImageUploadException("File " + filename + " exceeds the maximum size of 5 MB");
        }
    }

    public static void performAllvalidations(ArrayList<MultipartFile> allFiles) throws ImageUploadException {
        for (MultipartFile file:allFiles){
            validateFile(file);
        }
    }
}

