package com.socialMedia.BuzzHive.post.service;

import com.socialMedia.BuzzHive.post.modal.Image;
import com.socialMedia.BuzzHive.post.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
@Service
public class ImageService {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ImageRepo imageRepo;
    public ArrayList<String> setImages(ArrayList<MultipartFile> file, String postId)throws Exception {

        ArrayList<String> filePath = fileStorageService.saveFile(file);
        filePath.forEach(fileName -> {
            Image image = Image.builder()
                    .image_path(fileName)
                    .post_id(postId)
                    .build();
            imageRepo.save(image);
        });

        return filePath;

    }
}
