package com.example.twitterclonebe.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String name;
    private String description;
    private Date dateOfCreation;
    private Date dateOfBirth;
    private String location;
    private String profilePicture;
    private String headerPicture;
    private boolean isFollowing;
}
