import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { map, Observable } from 'rxjs';
import { PostResponse } from 'src/app/common/post-response';
import { User } from 'src/app/common/user';
import { AuthService } from 'src/app/services/auth.service';
import { PostService } from 'src/app/services/post.service';
import { UserService } from 'src/app/services/user.service';
import { BehaviorSubject, Subject, of } from 'rxjs';
import { NgxSpinnerService } from 'ngx-spinner';
import { HotToastService } from '@ngneat/hot-toast';
@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user: User;
  post: PostResponse;
  posts: PostResponse[] = [];
  isLoggedIn: string = 'false';
  loggedInUser: string = '';
  userArrived: boolean = false;
  postsArrived: boolean = false;
  constructor(private route: ActivatedRoute,
              private userService: UserService,
              private postService: PostService,
              private authService: AuthService,
              public router: Router,
              private toast: HotToastService) {
    this.user = {
      id: 0,
      username: "",
      name: "",
      description: "",
      dateOfCreation: new Date(),
      dateOfBirth: new Date(),
      location: "",
      profilePicture: "",
      headerPicture: "",
      following: false

    }
    this.post = {
      postId: "",
      description: "",
      photoLink: "",
      liked: false,
      saved: false,
      numOfLikes: 0,
      numOfComments: 0,
      userDTO: {
        id: 0,
        username: "",
        name: "",
        description: "",
        dateOfCreation: new Date(),
        dateOfBirth: new Date(),
        location: "",
        profilePicture: "",
        headerPicture: "",
        following: false
      }
    }

  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleUser();
      this.handlePosts();
    })
    this.authService.loggedIn.subscribe({
      next:value => {
        this.isLoggedIn=value;
        if(this.isLoggedIn){
          this.loggedInUser = JSON.parse(localStorage.getItem("user")!).username;
          
        }
      },
      error:err => {
        console.log(err);
      }
    });

  }

  handleUser () {

    const username: String = this.route.snapshot.paramMap.get('username')!;

    this.userService.getUserData(username).subscribe({
      next: data => {
        this.user = data;
        this.userArrived =true;
      }, error: err => {
        this.router.navigateByUrl('');
      }
    }
    )
  }

  handlePosts () {
    const username: String = this.route.snapshot.paramMap.get('username')!;

    this.postService.getPostsByUsername(username).subscribe(
      data => {
          this.posts = data;
          this.postsArrived = true;
      }
    )
  
  }

  followUser(username: string) {
      this.userService.followUser(username).subscribe(
        response => {
          if(response == "OK"){
            this.user.following = true;
          }
        }
      )
  }

  unfollowUser(username: string) {
    this.userService.unfollowUser(username).subscribe(
      response => {
        if(response == "OK"){
          this.user.following = false;
        }
      }
    )
  }

  getTweets() {
    this.handlePosts()
  }

  getLikes() {
    this.postService.getLikesPosts(this.user.username).subscribe(
      data => {
        this.posts = data;
      }
    )
  }

}
