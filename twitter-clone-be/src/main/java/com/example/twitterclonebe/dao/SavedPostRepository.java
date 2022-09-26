package com.example.twitterclonebe.dao;

import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import com.example.twitterclonebe.entities.SavedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SavedPostRepository extends JpaRepository<SavedPost, Long> {
    Optional<SavedPost> findTopByAppUserAndPost(AppUser user, Post post);
    @Transactional
    @Modifying
    @Query("delete from SavedPost s where s.appUser = ?1 and s.post = ?2")
    void removeByAppUserAndPost(AppUser user, Post post);
}
