import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { PostRequest } from 'src/app/common/post-request';
import { PostResponse } from 'src/app/common/post-response';
import { PostService } from 'src/app/services/post.service';

@Component({
  selector: 'app-compose-tweet',
  templateUrl: './compose-tweet.component.html',
  styleUrls: ['./compose-tweet.component.css']
})
export class ComposeTweetComponent implements OnInit {

  composeTweetFormGroup: FormGroup;
  postRequest: PostRequest;
  postImage: any;
  constructor(public dialogRef: MatDialogRef<ComposeTweetComponent>,
              private postService: PostService) {
    this.composeTweetFormGroup = new FormGroup({
      description: new FormControl('', Validators.required)
    })
    this.postRequest = {
      description: '',
      parentPostId: ''
    }
  }

  ngOnInit(): void {
  }

  onImageUploadTweet(image: any) {
    this.postImage = image.target.files[0];
    let element = document.getElementById('post-picture') as HTMLImageElement;
    element.src = URL.createObjectURL(image.target.files[0])
    element.style.display = 'block'
  }


  composeTweet() {
    this.postRequest.description = this.composeTweetFormGroup.get('description')?.value;
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
        this.dialogRef.close();
      }
    );
    
  }
}
