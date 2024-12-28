package com.socialMedia.BuzzHive.post.repository;

import com.socialMedia.BuzzHive.post.modal.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
   @Query(value = "SELECT * FROM buzzhive_schema.images where post_id=:postId ORDER BY id ASC ",nativeQuery = true)
    ArrayList<Image> getDataForParticularPost(String postId);
}
