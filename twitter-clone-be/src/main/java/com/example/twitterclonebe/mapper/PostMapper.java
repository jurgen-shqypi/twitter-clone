package com.example.twitterclonebe.mapper;

import com.example.twitterclonebe.dao.LikeRepository;
import com.example.twitterclonebe.dao.PostRepository;
import com.example.twitterclonebe.dao.SavedPostRepository;
import com.example.twitterclonebe.dto.PostDetailedDTO;
import com.example.twitterclonebe.dto.PostRequestDTO;
import com.example.twitterclonebe.dto.PostResponseDTO;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import com.example.twitterclonebe.service.AppUserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;


@Mapper(componentModel = "spring", imports = UUID.class)
public abstract class PostMapper {

    @Autowired
    LikeRepository likeRepository;
    @Autowired
    AppUserService appUserService;
    @Autowired
    SavedPostRepository savedPostRepository;
    @Autowired
    PostRepository postRepository;

    @Mapping(target = "id", expression = "java(String.valueOf(UUID.randomUUID().getMostSignificantBits()).replace(\"-\", \"\"))")
    @Mapping(target = "appUser", source = "user")
    @Mapping(target = "description", source = "postRequestDTO.description")
    @Mapping(target = "parentPost", expression = "java(postRepository.getPostById(postRequestDTO.getParentPostId()))")
    public abstract Post postRequestToPost(PostRequestDTO postRequestDTO, AppUser user);

    @Mapping(target = "postId", expression = "java(post.getId())")
    @Mapping(target = "userDTO", source = "appUser")
    @Mapping(target = "isLiked", expression = "java(checkIfLiked(post))")
    @Mapping(target = "isSaved", expression = "java(checkIfSaved(post))")
    @Mapping(target = "numOfLikes", expression = "java(post.getNumOfLikes())")
    public abstract PostResponseDTO postToDTO(Post post);

    @Mapping(target = "postId", expression = "java(post.getId())")
    public abstract List<PostResponseDTO> postListToPostDTO(List<Post> posts);

    @Mapping(target = "postId", expression = "java(post.getId())")
    @Mapping(target = "userDTO", source = "appUser")
    @Mapping(target = "isLiked", expression = "java(checkIfLiked(post))")
    @Mapping(target = "isSaved", expression = "java(checkIfSaved(post))")
    @Mapping(target = "numOfLikes", expression = "java(post.getNumOfLikes())")
    @Mapping(target = "comments", expression = "java(postListToPostDTO(postRepository.getPostByParentPost(post)))")
    public abstract PostDetailedDTO postToPostDetailed(Post post);

    protected boolean checkIfLiked(Post post){
        return likeRepository.findTopByAppUserAndPost(appUserService.getCurrentUser(), post).isPresent();
    }

    protected boolean checkIfSaved(Post post){
        return savedPostRepository.findTopByAppUserAndPost(appUserService.getCurrentUser(), post).isPresent();
    }
}
