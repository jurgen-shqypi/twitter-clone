package com.example.twitterclonebe.dto;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PostRequestDTO {
    private String description;
    private String photoLink;
    private String parentPostId;
}
