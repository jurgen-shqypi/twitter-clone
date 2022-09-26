package com.example.twitterclonebe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class EditProfileDTO {
    private String name;
    private String description;
    private Date dateOfBirth;
    private String location;
    private String profilePicture;
    private String headerPicture;
}
