package com.example.twitterclonebe.controller;


import com.example.twitterclonebe.dao.AppUserRepository;
import com.example.twitterclonebe.dao.UserFollowerRepository;
import com.example.twitterclonebe.dto.EditProfileDTO;
import com.example.twitterclonebe.dto.UsernameChangeDTO;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.UserFollower;
import com.example.twitterclonebe.mapper.UserMapper;
import com.example.twitterclonebe.service.AppUserService;
import com.example.twitterclonebe.service.CloudinaryService;
import com.example.twitterclonebe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    AppUserService appUserService;
    @Autowired
    PostService postService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    UserFollowerRepository userFollowerRepository;
    @Autowired
    CloudinaryService cloudinaryService;

    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping("/users/{username}")
    public ResponseEntity getUserDetailsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userMapper.userToDTO((AppUser) appUserService.loadUserByUsername(username)));
    }

    @GetMapping("/current/user")
    public ResponseEntity getLoggedInUser(@AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(userMapper.userToDTO(appUserService.findById(user.getId()).orElseThrow()));
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity searchUser(@AuthenticationPrincipal AppUser user, @PathVariable String keyword){
        Pageable pageable = PageRequest.of(0, 3);
        return ResponseEntity.ok(userMapper.appUserListToUserDTOList(userRepository.findByUsernameContaining(user, keyword, pageable)));
    }

    @PostMapping("/follow/{username}")
    public ResponseEntity followUser(@AuthenticationPrincipal AppUser userFollower, @PathVariable String username){
        AppUser user = (AppUser) appUserService.loadUserByUsername(username);
        UserFollower newUserFollower = new UserFollower();
        newUserFollower.setUser(user);
        newUserFollower.setUserFollower(userFollower);
        userFollowerRepository.save(newUserFollower);
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

    @PostMapping("/unfollow/{username}")
    public ResponseEntity unfollowUser(@AuthenticationPrincipal AppUser userFollower, @PathVariable String username){
        AppUser user = (AppUser) appUserService.loadUserByUsername(username);
        userFollowerRepository.removeByUserAndUserFollower(user, userFollower);
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

    @PostMapping("/edit-profile")
    public ResponseEntity editProfile(@AuthenticationPrincipal AppUser user, @RequestPart(name = "editRequestDTO") EditProfileDTO editProfileDTO,
                                      @RequestPart(name = "headerPicture", required = false) MultipartFile headerPicture,
                                      @RequestPart(name = "profilePicture", required = false) MultipartFile profilePicture){
        if (headerPicture != null) {
            String imgUrl = cloudinaryService.uploadFile(headerPicture, 1500, 500);
            editProfileDTO.setHeaderPicture(imgUrl);

        }
        if (profilePicture != null) {
            String imgUrl = cloudinaryService.uploadFile(profilePicture, 400, 400);
            editProfileDTO.setProfilePicture(imgUrl);
        }
        AppUser newUser = userMapper.editDTOToUser(editProfileDTO, user);
        appUserService.updateUser(newUser);
        return ResponseEntity.ok(userMapper.userToDTO(newUser));
    }

    @PostMapping("/change-username")
    public ResponseEntity changeUsername(@AuthenticationPrincipal AppUser user, @RequestBody UsernameChangeDTO usernameChangeDTO){
        if(usernameChangeDTO.getUsername().equals("test")) {
            return new ResponseEntity(
                    "You cannot change the username of the test account!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }else if(appUserService.userExists(usernameChangeDTO.getUsername())) {
            return new ResponseEntity(
                    "Username is taken!", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        user.setUsername(usernameChangeDTO.getUsername());
        userRepository.save(user);
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

    @GetMapping("/most-followed")
    public ResponseEntity getMostFollowedUsers(@AuthenticationPrincipal AppUser user) {
        Pageable pageable = PageRequest.of(0, 3);

        return ResponseEntity.ok(userMapper.appUserListToUserDTOList(userFollowerRepository.findMostFollowedUsers(user, pageable)));
    }
}
