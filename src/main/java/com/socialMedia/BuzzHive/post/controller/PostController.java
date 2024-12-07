package com.socialMedia.BuzzHive.post.controller;

import com.socialMedia.BuzzHive.post.modal.PostDTO;
import com.socialMedia.BuzzHive.post.service.FileStorageService;
import com.socialMedia.BuzzHive.post.service.PostService;
import com.socialMedia.BuzzHive.post.service.S3ImageUploader;
import com.socialMedia.BuzzHive.utils.ImageValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private PostService postService;
    @Autowired
    private S3ImageUploader s3ImageUploader;

    @PostMapping("/create")
    public ResponseEntity<?> uploadFile(
            @RequestParam("image") ArrayList<MultipartFile> file,
            @RequestParam("text_content") String text_content
    ) {
        try {

            PostDTO postDTO =  postService.createPost(file,text_content);

            return new ResponseEntity<>(postDTO,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }
}

