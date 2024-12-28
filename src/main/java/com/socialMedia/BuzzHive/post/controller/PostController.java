package com.socialMedia.BuzzHive.post.controller;

import com.socialMedia.BuzzHive.exception.PostNotFoundException;
import com.socialMedia.BuzzHive.post.modal.PostDTO;
import com.socialMedia.BuzzHive.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/posts/create")
    public ResponseEntity<?> uploadFile(
            @RequestParam(value = "image",required = false) ArrayList<MultipartFile> file,
            @RequestParam("text_content") String text_content,
            @RequestParam(value ="user_id") String user_id
    ) {
        try {
            PostDTO postDTO =  postService.createPost(file,text_content,user_id);
            return new ResponseEntity<>(postDTO,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> getDetailsForSinglePost(@PathVariable("postId") String postId) throws PostNotFoundException {
        return new ResponseEntity<>(postService.getDetailsOfPost(postId), HttpStatus.ACCEPTED);

    }
    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<?> getDetailsOfAllPosts(@PathVariable("userId") String userId) throws PostNotFoundException {
        return new ResponseEntity<>(postService.getUsersAllPost(userId), HttpStatus.ACCEPTED);
    }
}

