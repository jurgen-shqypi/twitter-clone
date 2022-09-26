package com.example.twitterclonebe.controller;

import com.example.twitterclonebe.dao.PostRepository;
import com.example.twitterclonebe.dao.SavedPostRepository;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import com.example.twitterclonebe.entities.SavedPost;
import com.example.twitterclonebe.mapper.PostMapper;
import com.example.twitterclonebe.mapper.SavedPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class SavedPostController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    SavedPostRepository savedPostRepository;
    @Autowired
    SavedPostMapper savedPostMapper;
    @Autowired
    PostMapper postMapper;

    @PostMapping("/save/{postId}")
    public ResponseEntity savePost(@AuthenticationPrincipal AppUser user, @PathVariable String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        SavedPost savedPost = savedPostMapper.saveRequestToSavedPost(post, user);
        savedPostRepository.save(savedPost);
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

    @PostMapping("/remove-save/{postId}")
    public ResponseEntity removeFromSaved(@AuthenticationPrincipal AppUser user, @PathVariable String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        SavedPost savedPost = savedPostMapper.saveRequestToSavedPost(post, user);
        savedPostRepository.removeByAppUserAndPost(user, post);
        return ResponseEntity.ok(HttpStatus.valueOf(200));
    }

    @GetMapping("/saved-posts")
    public ResponseEntity getSavedPosts(@AuthenticationPrincipal AppUser user) {
        return ResponseEntity.ok(postMapper.postListToPostDTO(postRepository.getPostBySavedPostsAndAppUser(user)));
    }
}
