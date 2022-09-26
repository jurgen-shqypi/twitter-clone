package com.example.twitterclonebe.mapper;

import com.example.twitterclonebe.dto.LikeResponseDTO;
import com.example.twitterclonebe.entities.AppLike;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class LikeMapper {

    @Autowired
    UserMapper userMapper;
    @Autowired
    PostMapper postMapper;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", source = "post")
    @Mapping(target = "appUser", source = "appUser")
    @Mapping(target = "dateCreated", ignore = true)
    public abstract AppLike likeRequestToLike(Post post, AppUser appUser);

    @Mapping(target = "postResponseDTO", expression = "java(postMapper.postToDTO(like.getPost()))")
    public abstract LikeResponseDTO likeToLikeResponseDTO(AppLike like);
}
