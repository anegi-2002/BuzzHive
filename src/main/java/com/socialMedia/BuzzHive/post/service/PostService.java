package com.socialMedia.BuzzHive.post.service;

import com.socialMedia.BuzzHive.post.modal.Post;
import com.socialMedia.BuzzHive.post.modal.PostDTO;
import com.socialMedia.BuzzHive.post.repository.PostsRepo;
import com.socialMedia.BuzzHive.utils.ImageValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    PostsRepo postsRepo;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ImageService imageService;

    public PostDTO createPost(ArrayList<MultipartFile> file,String postcontent) throws Exception {
        ImageValidation.performAllvalidations(file);

        Post post=new Post();
        post.setPost_id(UUID.randomUUID().toString());
        post.setText_content(postcontent);
        //Need to verufy this later.
        post.setUser_id(UUID.randomUUID().toString());
        postsRepo.save(post);
        ArrayList<String> imagePath= imageService.setImages(file,post.getPost_id());

        PostDTO postDTO = new PostDTO();
        postDTO.setPost_id(post.getPost_id());
        postDTO.setMessage("File uploaded successfully");
        postDTO.setImages(imagePath);
        return postDTO;
    }

}
