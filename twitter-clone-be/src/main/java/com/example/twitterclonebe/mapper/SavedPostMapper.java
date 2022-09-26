package com.example.twitterclonebe.mapper;

import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import com.example.twitterclonebe.entities.SavedPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SavedPostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", source = "post")
    @Mapping(target = "appUser", source = "appUser")
    @Mapping(target = "dateCreated", ignore = true)
    SavedPost saveRequestToSavedPost(Post post, AppUser appUser);

}
