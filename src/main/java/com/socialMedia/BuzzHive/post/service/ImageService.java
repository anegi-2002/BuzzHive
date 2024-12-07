package com.socialMedia.BuzzHive.post.service;

import com.socialMedia.BuzzHive.exception.ImageUploadException;
import com.socialMedia.BuzzHive.post.modal.Image;
import com.socialMedia.BuzzHive.post.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private S3ImageUploader s3ImageUploader;
    @Autowired
    private ImageRepo imageRepo;
    public ArrayList<String> setImages(ArrayList<MultipartFile> file, String postId)throws ImageUploadException {

        ArrayList<String> filePath = new ArrayList<>();
        for(MultipartFile multipartFile:file){
            filePath.add(s3ImageUploader.upload(multipartFile));
        }

        List<Image> images = filePath.stream()
                .map(filePath1 -> new Image().builder()
                        .image_path(filePath1)
                        .post_id(postId)
                        .build())
                .collect(Collectors.toList());
        imageRepo.saveAll(images);

        return filePath;

    }
}
