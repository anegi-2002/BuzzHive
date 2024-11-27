package com.socialMedia.BuzzHive.post.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Table(name = "images", schema = "buzzhive_schema")
@Entity
@Data
@Builder

@AllArgsConstructor

public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //  @SequenceGenerator(name = "cart_id_seq", sequenceName = "retail_schema.cart_id_seq", allocationSize = 1, initialValue = 1)
    private long id;
    private String post_id;
    private String image_path;
    @Column(name = "uploaded_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp uploaded_at;

    public Image() {
        this.uploaded_at = Timestamp.from(java.time.Instant.now());
    }

}
