package com.socialMedia.BuzzHive.post.repository;

import com.socialMedia.BuzzHive.post.modal.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepo extends JpaRepository<Post, Long> {

}
