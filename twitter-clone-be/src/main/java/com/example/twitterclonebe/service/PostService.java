package com.example.twitterclonebe.service;

import com.example.twitterclonebe.dao.AppUserRepository;
import com.example.twitterclonebe.dao.PostRepository;
import com.example.twitterclonebe.dto.PostRequestDTO;
import com.example.twitterclonebe.dto.PostResponseDTO;
import com.example.twitterclonebe.dto.UserDTO;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import com.example.twitterclonebe.mapper.PostMapper;
import com.example.twitterclonebe.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;
    @Autowired
    PostMapper postMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AppUserRepository userRepository;
    @Autowired
    AppUserService userService;

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO, AppUser user) {
        Post post = postMapper.postRequestToPost(postRequestDTO, user);
        postRepository.save(post);
        PostResponseDTO postResponseDTO = postMapper.postToDTO(post);
        return postResponseDTO;
    }

    @Transactional
    public List<PostResponseDTO> getAllPostsFromUser(AppUser user){
        List<Post> posts = postRepository.findAllByAppUser(user);
        return postMapper.postListToPostDTO(posts);
    }

    @Transactional
    public List<UserDTO> getUsersByPostLiked(String postId) {
        Post post = postRepository.getPostById(postId);
        return userMapper.appUserListToUserDTOList(userRepository.getAppUserByPostsLiked(post));
    }

    @Transactional
    public List<PostResponseDTO> getLikedPosts(String username) {
        return postMapper.postListToPostDTO(postRepository.getPostByLikedPostsAndAppUser((AppUser) userService.loadUserByUsername(username)));
    }

}
