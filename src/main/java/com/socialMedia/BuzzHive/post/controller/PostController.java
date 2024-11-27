package com.socialMedia.BuzzHive.post.controller;

import com.socialMedia.BuzzHive.post.modal.PostDTO;
import com.socialMedia.BuzzHive.post.service.FileStorageService;
import com.socialMedia.BuzzHive.post.service.PostService;
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

    @PostMapping("/create")
    public ResponseEntity<PostDTO> uploadFile(
            @RequestParam("image") ArrayList<MultipartFile> file,
            @RequestParam("text_content") String text_content
    ) {
        try {
            PostDTO postDTO =  postService.createPost(file,text_content);
            return new ResponseEntity<>(postDTO,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}

