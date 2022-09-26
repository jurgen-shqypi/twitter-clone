package com.example.twitterclonebe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class LikeResponseDTO {
    private Long likeId;
    private PostResponseDTO postResponseDTO;
}
