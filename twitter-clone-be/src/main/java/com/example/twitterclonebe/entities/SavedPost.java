package com.example.twitterclonebe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "saved_post",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"post_id", "app_user_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SavedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @CreationTimestamp
    private Date dateCreated;
}
