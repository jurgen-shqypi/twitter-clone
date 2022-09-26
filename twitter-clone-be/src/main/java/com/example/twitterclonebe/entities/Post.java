package com.example.twitterclonebe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id @Column(name = "id")
    private String id;
    @Column(name = "description")
    private String description;
    @Column(name = "photo_link")
    private String photoLink;
    @CreationTimestamp
    private Date dateCreated;
    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<AppLike> likes = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<SavedPost> savedPosts = new HashSet<>();

    @ManyToOne
    @JoinColumn
    private Post parentPost;

    @OneToMany(mappedBy = "parentPost", cascade = CascadeType.ALL)
    private List<Post> subPosts = new ArrayList<>();

    public void addLike(AppLike like){
        this.likes.add(like);
    }

    public void addSavedPost(SavedPost post){
        this.savedPosts.add(post);
    }

    public void addComment(Post post){
        subPosts.add(post);
    }

    public int getNumOfLikes(){
        return this.likes.size();
    }

    public int getNumOfComments(){
        return this.subPosts.size();
    }


}
