package com.example.twitterclonebe.dao;

import com.example.twitterclonebe.entities.AppLike;
import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<AppLike, Long> {
    Optional<AppLike> findTopByAppUserAndPost(AppUser appUser, Post post);
    @Transactional
    @Modifying
    @Query("delete from AppLike a where a.appUser = ?1 and a.post = ?2")
    void removeByAppUserAndPost(AppUser user, Post post);

}
