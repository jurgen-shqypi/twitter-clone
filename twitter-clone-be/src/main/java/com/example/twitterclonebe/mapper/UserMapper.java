package com.example.twitterclonebe.mapper;

import com.example.twitterclonebe.dao.UserFollowerRepository;
import com.example.twitterclonebe.dto.EditProfileDTO;
import com.example.twitterclonebe.dto.LoginDTO;
import com.example.twitterclonebe.dto.SignupDTO;
import com.example.twitterclonebe.dto.UserDTO;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.service.AppUserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    AppUserService appUserService;
    @Autowired
    UserFollowerRepository userFollowerRepository;

    @Mapping(target = "isFollowing", expression = "java(checkIfFollowing(user))")
    public abstract UserDTO userToDTO(AppUser user);

    public abstract AppUser signupDTOToUser(SignupDTO signupDTO);
    public abstract AppUser loginDTOToUser(LoginDTO loginDTO);
    public abstract List<UserDTO> appUserListToUserDTOList(List<AppUser> users);

    @Mapping(target = "name", source = "editProfileDTO.name")
    @Mapping(target = "profilePicture", source = "editProfileDTO.profilePicture")
    @Mapping(target = "headerPicture", source = "editProfileDTO.headerPicture")
    @Mapping(target = "description", source = "editProfileDTO.description")
    @Mapping(target = "location", source = "editProfileDTO.location")
    @Mapping(target = "dateOfBirth", source = "editProfileDTO.dateOfBirth")
    public abstract AppUser editDTOToUser(EditProfileDTO editProfileDTO, @MappingTarget AppUser user);

    boolean checkIfFollowing(AppUser user) {
        AppUser userFollowing = appUserService.getCurrentUser();
        return userFollowerRepository.findTopByUserAndUserFollower(user, userFollowing).isPresent();
    }

}
