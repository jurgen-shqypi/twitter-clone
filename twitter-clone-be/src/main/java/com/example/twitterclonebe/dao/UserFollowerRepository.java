package com.example.twitterclonebe.dao;

import com.example.twitterclonebe.entities.AppUser;
import com.example.twitterclonebe.entities.UserFollower;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {
    Optional<UserFollower> findTopByUserAndUserFollower(AppUser user, AppUser userFollower);

    @Transactional
    @Modifying
    @Query("delete from UserFollower u where u.user = ?1 and u.userFollower = ?2")
    void removeByUserAndUserFollower(AppUser user, AppUser userFollower);

    @Query("select a from UserFollower u inner join AppUser a on a = u.user " +
            "where u.user not in (select us.user from UserFollower us where us.userFollower = ?1)" +
            " group by u.user order by count(u.user) DESC")
    List<AppUser> findMostFollowedUsers(AppUser user, Pageable pageable);
}
