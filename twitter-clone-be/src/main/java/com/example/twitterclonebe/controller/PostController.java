package com.example.twitterclonebe.controller;

import com.example.twitterclonebe.dao.PostRepository;
import com.example.twitterclonebe.dto.PostRequestDTO;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.mapper.PostMapper;
import com.example.twitterclonebe.service.AppUserService;
import com.example.twitterclonebe.service.CloudinaryService;
import com.example.twitterclonebe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    AppUserService userService;
    @Autowired
    AppUserService appUserService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    PostMapper postMapper;

    @PostMapping("/compose/post")
    public ResponseEntity createPost(@AuthenticationPrincipal AppUser user, @RequestPart("postRequestDTO") PostRequestDTO postRequestDTO, @RequestPart(name = "file",required = false) MultipartFile file) {
        String imgUrl;
        if(file != null){
            imgUrl = cloudinaryService.uploadFile(file, 700, 650);
            postRequestDTO.setPhotoLink(imgUrl);
        }

        AppUser userPosting = appUserService.findById(user.getId()).orElseThrow();
        return ResponseEntity.ok(postService.createPost(postRequestDTO, userPosting));
    }


    @GetMapping("/posts/{username}")
    public ResponseEntity getPostsByUsername(@PathVariable String username) {
        AppUser user = (AppUser) userService.loadUserByUsername(username);
        return ResponseEntity.ok(postService.getAllPostsFromUser(user));
    }

    @GetMapping("post/{postId}")
    public ResponseEntity getPostById(@PathVariable String postId) {
        return ResponseEntity.ok(postMapper.postToPostDetailed(postRepository.findById(postId).orElseThrow(() -> {throw new RuntimeException("Post not found");})));
    }

    @GetMapping("/posts-following")
    public ResponseEntity getPostsFromFollowing(@AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(postMapper.postListToPostDTO(postRepository.getFollowingUsersPosts(user)));
    }

}
