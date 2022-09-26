package com.example.twitterclonebe.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "applike",
        uniqueConstraints =
            @UniqueConstraint(columnNames = {"post_id", "app_user_id"}))
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AppLike {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private Date dateCreated;

    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(optional = false)
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;
}
