package com.example.twitterclonebe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "appuser")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AppUser implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "description")
    private String description;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "location")
    private String location;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "header_picture")
    private String headerPicture;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "appUser")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<AppLike> likes = new HashSet<>();

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<SavedPost> savedPosts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserFollower> following;

    @OneToMany(mappedBy = "userFollower", cascade = CascadeType.ALL)
    private Set<UserFollower> followers;

    @Override
    public Collection<Role> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void addRole(Role role){
        roles.add(role);
    }

    public void addLike(AppLike like){
        likes.add(like);
    }

    public void addSavedPost(SavedPost post){
        savedPosts.add(post);
    }

}
