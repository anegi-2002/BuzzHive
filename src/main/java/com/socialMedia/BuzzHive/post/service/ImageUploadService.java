package com.socialMedia.BuzzHive.post.service;

import com.socialMedia.BuzzHive.exception.ImageUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageUploadService {
    String upload(MultipartFile file) throws IOException, ImageUploadException;
    String preSignedUrl(String filename);
    List<String> allfiles();
}
