import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LikeRequest } from '../common/like-request';
import { PostDetailed } from '../common/post-detailed';
import { PostRequest } from '../common/post-request';
import { PostResponse } from '../common/post-response';
import { User } from '../common/user';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  

  likeRequest: LikeRequest = new LikeRequest();

  constructor(private http: HttpClient) { }

  postTweet(formData: FormData) {
    const postTweetLink = environment.serverUrl + 'compose/post';
    return this.http.post<PostResponse>(postTweetLink, formData);
  }

  getPostsByUsername(username: String) {
    const postsLink: string = environment.serverUrl + `posts/${username}`;
    return this.http.get<PostResponse[]>(postsLink);
  }

  likePost(post: PostResponse){
    const likePostUrl: string = environment.serverUrl + `like/${post.postId}`;
    return this.http.post<string>(likePostUrl, {});
  }

  removeLike(post: PostResponse){
    const likePostUrl: string = environment.serverUrl + `remove-like/${post.postId}`;
    return this.http.post<string>(likePostUrl, {});
  }

  savePost(post: PostResponse) {
    const savePostUrl: string = environment.serverUrl + `save/${post.postId}`;
    return this.http.post<string>(savePostUrl, {});
  }

  removeSave(post: PostResponse) {
    const savePostUrl: string = environment.serverUrl + `remove-save/${post.postId}`;
    return this.http.post<string>(savePostUrl, {});
  }

  getFollowingUsersPosts() {
    const url: string = environment.serverUrl + 'posts-following';
    return this.http.get<PostResponse[]>(url);
  }

  getSavedPosts() {
    const url: string = environment.serverUrl + 'saved-posts';
    return this.http.get<PostResponse[]>(url);
  }

  getLikesPosts(username: string) {
    const url: string = environment.serverUrl + 'liked-posts' + `/${username}`;
    return this.http.get<PostResponse[]>(url);
  }

  getUsersWhoLiked(postId: string) {
    const url: string = environment.serverUrl + `likes-user/${postId}`;
    return this.http.get<User[]>(url);
  }

  getPostById(postId: string){
    const url: string = environment.serverUrl + `post/${postId}`;
    return this.http.get<PostDetailed>(url);
  }
}
