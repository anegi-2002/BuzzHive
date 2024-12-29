package com.socialMedia.BuzzHive.post.service;

import com.socialMedia.BuzzHive.exception.ImageUploadException;
import com.socialMedia.BuzzHive.post.modal.Image;
import com.socialMedia.BuzzHive.post.repository.ImageRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
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
        try {
            List<Image> images = file.stream()
                    .map(file1 -> {
                                String actualFileName = file1.getOriginalFilename();
                                String newFileName = UUID.randomUUID().toString() + actualFileName.substring(actualFileName.lastIndexOf("."));
                                String presignedUrl = s3ImageUploader.upload(file1, newFileName);
                                return Image.builder()
                                        .image_path(presignedUrl)
                                        .post_id(postId)
                                        .file_name(newFileName)
                                        .uploaded_at(Timestamp.from(java.time.Instant.now()))
                                        .build();
                            }
                    ).toList();
            imageRepo.saveAll(images);

            return new ArrayList<>(images.stream()
                    .map(Image::getImage_path)
                    .toList());
        }
        catch (Exception e){
            throw new ImageUploadException("Failed to upload images");
        }

    }
    public ArrayList<Image> getImageDataForParticularPost(String postId){
        return imageRepo.getDataForParticularPost(postId);
    }
    @Transactional
    public void updateImageUrl(long id, String preSignedNewUrl) {
        imageRepo.updateImageUrl(id,preSignedNewUrl);
    }
}
