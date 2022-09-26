package com.example.twitterclonebe.controller;

import com.example.twitterclonebe.dao.LikeRepository;
import com.example.twitterclonebe.dao.PostRepository;
import com.example.twitterclonebe.entities.AppLike;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import com.example.twitterclonebe.mapper.LikeMapper;
import com.example.twitterclonebe.mapper.PostMapper;
import com.example.twitterclonebe.mapper.UserMapper;
import com.example.twitterclonebe.service.AppUserService;
import com.example.twitterclonebe.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LikeController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AppUserService appUserService;
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostMapper postMapper;
    @Autowired
    PostService postService;


    @PostMapping("/like/{postId}")
    public ResponseEntity likePost(@AuthenticationPrincipal AppUser user, @PathVariable String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        AppLike like = likeMapper.likeRequestToLike(post, user);
        likeRepository.save(like);
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

    @PostMapping("/remove-like/{postId}")
    public ResponseEntity removeLike(@AuthenticationPrincipal AppUser user, @PathVariable String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        likeRepository.removeByAppUserAndPost(user, post);
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

    @GetMapping("/liked-posts/{username}")
    public ResponseEntity getLikedPosts(@PathVariable String username) {
        return ResponseEntity.ok(postService.getLikedPosts(username));
    }

    @GetMapping("/likes-user/{postId}")
    public ResponseEntity getUsersByPostLikes(@PathVariable String postId) {
        return ResponseEntity.ok(postService.getUsersByPostLiked(postId));
    }

}
