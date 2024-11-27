package com.socialMedia.BuzzHive.post.repository;

import com.socialMedia.BuzzHive.post.modal.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
}
