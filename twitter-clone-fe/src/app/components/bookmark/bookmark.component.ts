import { Component, OnInit } from '@angular/core';
import { PostResponse } from 'src/app/common/post-response';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-bookmark',
  templateUrl: './bookmark.component.html',
  styleUrls: ['./bookmark.component.css']
})
export class BookmarkComponent implements OnInit {

  posts: PostResponse[] = [];

  constructor(private postService: PostService) { }

  ngOnInit(): void {
    this.getSavedPosts();
  }

  getSavedPosts(){
    this.postService.getSavedPosts().subscribe(
      data => {
        this.posts = data;
      }
    )
  }

}
