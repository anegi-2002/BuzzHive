package com.socialMedia.BuzzHive.post.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    // Path to the uploads directory
    private final String uploadDir = "uploads/";

    public ArrayList<String> saveFile(ArrayList<MultipartFile> file) throws IOException {

        ArrayList<String> fileNameArray =  new ArrayList<>();
        file.forEach(multipartFile -> {
            String fileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
            try {

                Path folderPath = Paths.get(uploadDir);
                if (!Files.exists(folderPath)) {
                    Files.createDirectories(folderPath); // Ensure the directory exists
                }

                // Define the file path
                Path filePath = folderPath.resolve(fileName);

                // Save the file to the directory
                Files.write(filePath, multipartFile.getBytes());
                fileNameArray.add(uploadDir + fileName);
            }catch (IOException ex){
                ex.printStackTrace();
            }
        });

        return fileNameArray;
    }
}

