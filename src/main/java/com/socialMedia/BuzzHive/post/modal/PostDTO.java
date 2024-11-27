package com.socialMedia.BuzzHive.post.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private String message;
    private String post_id;
    private ArrayList<String> images;
}
