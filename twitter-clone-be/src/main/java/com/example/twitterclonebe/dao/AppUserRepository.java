package com.example.twitterclonebe.dao;

import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select a from AppUser a LEFT JOIN UserFollower u ON u.user = a  " +
            "where a.username like concat('%', ?2, '%') AND a <> ?1  GROUP BY a order by count(a) DESC")
    List<AppUser> findByUsernameContaining(AppUser appUser, String username, Pageable pageable);

    @Query("select a from AppUser a inner join AppLike l on l.appUser = a where l.post = ?1")
    List<AppUser> getAppUserByPostsLiked(Post post);

    AppUser findByEmail(String email);
}