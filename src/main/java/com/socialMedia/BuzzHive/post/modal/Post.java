package com.socialMedia.BuzzHive.post.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Table(name = "posts", schema = "buzzhive_schema")
@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  @SequenceGenerator(name = "cart_id_seq", sequenceName = "retail_schema.cart_id_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    private String text_content;
    @Column(name = "post_id")
    private String post_id;
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    public Post() {
        this.createdAt = Timestamp.from(java.time.Instant.now());
    }
}
