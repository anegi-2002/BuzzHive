package com.socialMedia.BuzzHive.post.service;

import com.socialMedia.BuzzHive.exception.PostNotFoundException;
import com.socialMedia.BuzzHive.post.modal.Image;
import com.socialMedia.BuzzHive.post.modal.Post;
import com.socialMedia.BuzzHive.post.modal.PostDTO;
import com.socialMedia.BuzzHive.post.repository.PostsRepo;
import com.socialMedia.BuzzHive.service.UserService;
import com.socialMedia.BuzzHive.utils.ImageValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    PostsRepo postsRepo;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private UserService userService;

    public PostDTO createPost(ArrayList<MultipartFile> file,String postcontent,String user_id) throws Exception {
        if(file!=null && !file.isEmpty())
            ImageValidation.performAllvalidations(file);

        Post post=new Post();
        post.setPost_id(UUID.randomUUID().toString());
        post.setText_content(postcontent);
        post.setUser_id(user_id);
        postsRepo.save(post);
        ArrayList<String> imagePath = new ArrayList<>();
        PostDTO postDTO = new PostDTO();
        if(file!=null && !file.isEmpty())
            imagePath= imageService.setImages(file,post.getPost_id());

        postDTO.setPost_id(post.getPost_id());
        postDTO.setMessage("File uploaded successfully");
        postDTO.setImages(imagePath);
        return postDTO;
    }

    public PostDTO getDetailsOfPost(String postId) throws PostNotFoundException {
        Post postDetails = postsRepo.findByPost_id(postId);

        if (postDetails == null) {
            throw new PostNotFoundException("Post not found with postId: " + postId);
        }

        List<Image> imageData = imageService.getImageDataForParticularPost(postId);
        ArrayList<String> imageUrls = (imageData != null) ? imageData.stream()
                .map(Image::getImage_path)
                .collect(Collectors.toCollection(ArrayList::new))
                : new ArrayList<>();

        return new PostDTO(postDetails.getText_content(), postDetails.getPost_id(), imageUrls);
    }
    public ArrayList<PostDTO> getUsersAllPost(String userID) {
        ArrayList<Post> postDetails = postsRepo.findByUserId(userID);
        ArrayList<PostDTO> allPostDetails = new ArrayList<>();
        if(postDetails!=null && !postDetails.isEmpty()){
            for(Post postData:postDetails) {
                List<Image> imageData = imageService.getImageDataForParticularPost(postData.getPost_id());
                ArrayList<String> imageUrls = (imageData != null) ? imageData.stream()
                        .map(Image::getImage_path)
                        .collect(Collectors.toCollection(ArrayList::new))
                        : new ArrayList<>();

                allPostDetails.add(new PostDTO(postData.getText_content(), postData.getPost_id(), imageUrls));
            }
        }
        return allPostDetails;
    }
}
