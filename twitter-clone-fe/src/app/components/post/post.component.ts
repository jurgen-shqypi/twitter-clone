import { Component, OnInit, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PostResponse } from 'src/app/common/post-response';
import { User } from 'src/app/common/user';
import { PostService } from 'src/app/services/post.service';
import { UserLikedListComponent } from '../user-liked-list/user-liked-list.component';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  @Input() post: PostResponse = new PostResponse;

  constructor(private postService: PostService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  likePost(post: PostResponse) {
    if(!post.liked){
      this.postService.likePost(post).subscribe(
        data => {
          console.log(data);
          post.liked = true;
        }
      )
    } else {
      this.postService.removeLike(post).subscribe(
        data => {
          console.log(data);
          post.liked = false;
        }
      )
    }
    
  }

  savePost(post: PostResponse) {
    if(!post.saved){
      this.postService.savePost(post).subscribe(
        data => {
          console.log(data);
          post.saved = true;
        }
      )
    } else {
      this.postService.removeSave(post).subscribe(
        data => {
          console.log(data);
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

}
