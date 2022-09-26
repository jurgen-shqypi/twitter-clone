package com.example.twitterclonebe.dao;

import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    @Query("select p from Post p where p.appUser = ?1 and p.parentPost is null")
    List<Post> findAllByAppUser(AppUser user);
    Post getPostById(String id);

    @Query("SELECT p FROM AppUser a INNER JOIN UserFollower u ON u.userFollower = a " +
            "INNER JOIN Post p ON u.user = p.appUser WHERE a = ?1 and p.parentPost is null ORDER BY p.dateCreated DESC")
    List<Post> getFollowingUsersPosts(AppUser user);

    @Query("select p FROM AppUser a INNER JOIN SavedPost s ON s.appUser = a " +
            "INNER JOIN Post p ON p = s.post WHERE a = ?1 ORDER BY s.dateCreated DESC")
    List<Post> getPostBySavedPostsAndAppUser(AppUser user);

    @Query("select p FROM AppUser a INNER JOIN AppLike l ON l.appUser = a " +
            "INNER JOIN Post p ON p = l.post WHERE a = ?1 and p.parentPost is null ORDER BY l.dateCreated DESC ")
    List<Post> getPostByLikedPostsAndAppUser(AppUser user);

    List<Post> getPostByParentPost(Post post);
}
