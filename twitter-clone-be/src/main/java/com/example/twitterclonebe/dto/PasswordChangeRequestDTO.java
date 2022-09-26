package com.example.twitterclonebe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PasswordChangeRequestDTO {
    private String oldPassword;
    private String newPassword;
}
