package com.example.twitterclonebe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {
    private String email;
    private String name;
    private String username;
    private String password;
    private Date dateOfBirth;
    private String headerPicture;
    private String profilePicture;
}
