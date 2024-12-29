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
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private S3ImageUploader s3ImageUploader;

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
        try {
            List<Image> imageData = imageService.getImageDataForParticularPost(postId);
            ArrayList<String> imageUrls =  getImagePreSignedUrl(imageData);
            return new PostDTO(postDetails.getText_content(), postDetails.getPost_id(), imageUrls);
        } catch (Exception e) {
            throw new PostNotFoundException("Error while fetching the data");
        }
    }

    public ArrayList<String> getImagePreSignedUrl(List<Image> imageData){
        ArrayList<String> imageUrls = new ArrayList<>();
        imageData.forEach(image -> {
            if (image.getUploaded_at().toInstant().isAfter(Instant.now().minus(Duration.ofDays(7)))) {
                imageUrls.add(image.getImage_path());
            } else {
                String preSignedNewUrl = s3ImageUploader.preSignedUrl(image.getFile_name());
                imageUrls.add(preSignedNewUrl);
                imageService.updateImageUrl(image.getId(), preSignedNewUrl);
            }
        });
        return imageUrls;
    }
    public ArrayList<PostDTO> getUsersAllPost(String userID) {
        ArrayList<Post> postDetails = postsRepo.findByUserId(userID);
        ArrayList<PostDTO> allPostDetails = new ArrayList<>();
        if(postDetails!=null && !postDetails.isEmpty()){
            for(Post postData:postDetails) {
                List<Image> imageData = imageService.getImageDataForParticularPost(postData.getPost_id());
                ArrayList<String> imageUrls =  getImagePreSignedUrl(imageData);
                allPostDetails.add(new PostDTO(postData.getText_content(), postData.getPost_id(), imageUrls));
            }
        }
        return allPostDetails;
    }
}
