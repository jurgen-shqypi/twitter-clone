package com.example.twitterclonebe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostResponseDTO {
    private String postId;
    private String description;
    private String photoLink;
    private UserDTO userDTO;
    private boolean isLiked;
    private boolean isSaved;
    private int numOfLikes;
    private int numOfComments;
}
