package com.socialMedia.BuzzHive.post.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.socialMedia.BuzzHive.exception.ImageUploadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class S3ImageUploader implements ImageUploadService{
    @Autowired
    private AmazonS3 client;
    @Value("${app.s3.bucket}")
    private String bucketName;

    @Override
    public String upload(MultipartFile file, String filename) throws ImageUploadException {

        ObjectMetadata objectMetadata= new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        try {
            PutObjectResult putObjectResult = client.putObject(new PutObjectRequest(bucketName,filename,file.getInputStream(),objectMetadata));
            return preSignedUrl(filename);
        } catch (IOException e) {
            throw new ImageUploadException("Failed to upload image"+filename);
        }

    }

    @Override
    public String preSignedUrl(String filename) {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(6); // Add 7 days
        Date newDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,filename).withExpiration(newDate);
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    @Override
    public List<String> allfiles() {
        return List.of();
    }
}
