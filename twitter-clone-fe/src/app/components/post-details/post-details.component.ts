import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute} from '@angular/router';
import { PostDetailed } from 'src/app/common/post-detailed';
import { PostRequest } from 'src/app/common/post-request';
import { PostResponse } from 'src/app/common/post-response';
import { User } from 'src/app/common/user';
import { PostService } from 'src/app/services/post.service';
import { UserLikedListComponent } from '../user-liked-list/user-liked-list.component';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {

  post: PostDetailed = new PostDetailed;
  commentFormGroup: FormGroup;
  commentRequest: PostRequest;
  postImage: any;
  userDetails: User = JSON.parse(localStorage.getItem('user')!);
  constructor(private postService: PostService,
              private route: ActivatedRoute,
              public dialog: MatDialog,
              private location: Location) {
    this.commentFormGroup = new FormGroup({
      description: new FormControl('', Validators.required)
    })
    this.postService.getPostById(this.route.snapshot.paramMap.get('postId')!).subscribe(
      data => {
        this.post = data;
      }
    );
    this.commentRequest = {
      description: '',
      parentPostId: ''
    }
  }

  ngOnInit(): void {
  }

  likePost(post: PostResponse) {
    if(!post.liked){
      this.postService.likePost(post).subscribe(
        data => {
          post.liked = true;
        }
      )
    } else {
      this.postService.removeLike(post).subscribe(
        data => {
          post.liked = false;
        }
      )
    }
    
  }

  savePost(post: PostResponse) {
    if(!post.saved){
      this.postService.savePost(post).subscribe(
        data => {
          post.saved = true;
        }
      )
    } else {
      this.postService.removeSave(post).subscribe(
        data => {
          post.saved = false;
        }
      )
    }
    
  }

  getUsersWhoLiked(postId: string) {
    this.postService.getUsersWhoLiked(postId).subscribe(
      data => {
        this.dialog.open(UserLikedListComponent, {
          panelClass: 'panel',
          data: {usersData: data}
        });
      }
    )
    
  }

  goBack() {
    this.location.back();
  }

  postTweet() {
    this.commentRequest.description = this.commentFormGroup.get('description')?.value;
    this.commentRequest.parentPostId = this.post.postId;
    const formData = new FormData()
    formData.append("file", this.postImage)
    formData.append('postRequestDTO', new Blob([JSON
      .stringify(this.commentRequest)], {
      type: 'application/json'
    }));
    this.postService.postTweet(formData).subscribe(
      data => {
        this.commentFormGroup.reset()
      }
    );
  }

  onImageUpload(image: any) {
    this.postImage = image.target.files[0];
    let element = document.getElementById('post-pic') as HTMLImageElement;
    element.src = URL.createObjectURL(image.target.files[0])
    element.style.display = 'block'
  }

}
