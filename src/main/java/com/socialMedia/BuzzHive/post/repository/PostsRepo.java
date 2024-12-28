package com.socialMedia.BuzzHive.post.repository;

import com.socialMedia.BuzzHive.post.modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PostsRepo extends JpaRepository<Post, Long> {
    @Query(value = "SELECT * FROM buzzhive_schema.posts  WHERE post_id = :postId",nativeQuery = true)
    Post findByPost_id(@Param("postId")String postId);
    @Query(value = "SELECT * FROM buzzhive_schema.posts WHERE user_id = :userID",nativeQuery = true)
    ArrayList<Post> findByUserId(@Param("userID")String userID);
}
