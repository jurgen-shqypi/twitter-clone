package com.example.twitterclonebe.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class PostDetailedDTO {
    private String postId;
    private String description;
    private String photoLink;
    private UserDTO userDTO;
    private boolean isLiked;
    private boolean isSaved;
    private int numOfLikes;
    private int numOfComments;
    private List<PostResponseDTO> comments;
}
