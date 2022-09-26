import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { PostRequest } from 'src/app/common/post-request';
import { PostResponse } from 'src/app/common/post-response';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  postFormGroup: FormGroup;
  postRequest: PostRequest;
  postImage: any;
  posts: PostResponse[] = [];

  constructor(private postService: PostService) {
    this.postFormGroup = new FormGroup({
      description: new FormControl('', Validators.required)
    })
    this.postRequest = {
      description: '',
      parentPostId: ''
    }
   }

  ngOnInit(): void {
    this.retrievePostsFromFollowing()
  }

  onImageUpload(image: any) {
    this.postImage = image.target.files[0];
    let element = document.getElementById('post-pic') as HTMLImageElement;
    element.src = URL.createObjectURL(image.target.files[0])
    element.style.display = 'block'
  }


  postTweet() {
    this.postRequest.description = this.postFormGroup.get('description')?.value;
    console.log(this.postImage);
    const formData = new FormData()
    formData.append("file", this.postImage)
    formData.append('postRequestDTO', new Blob([JSON
      .stringify(this.postRequest)], {
      type: 'application/json'
    }));
    this.postService.postTweet(formData).subscribe(
      data => {
        console.log(data)
        this.postFormGroup.reset()
      }
    );
    
  }

  retrievePostsFromFollowing() {
    this.postService.getFollowingUsersPosts().subscribe(
      data => {
        this.posts = data;
      }
    )
  }


}
