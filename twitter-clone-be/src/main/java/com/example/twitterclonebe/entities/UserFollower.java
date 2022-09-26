package com.example.twitterclonebe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "user_follower",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"user_id", "user_follower_id"}))
public class UserFollower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne
    @JoinColumn(name = "user_follower_id")
    private AppUser userFollower;

}
