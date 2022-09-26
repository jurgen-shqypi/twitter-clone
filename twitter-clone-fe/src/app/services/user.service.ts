import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { User } from '../common/user';
import { UsernameChangeDTO } from '../common/username-change-dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  

  constructor(private http: HttpClient) {
    
   }

  getUserData(username: String) {

    const userUrl = environment.serverUrl + `users/${username}`;

    return this.http.get<User>(userUrl);
  }

  searchUser(keyword: string){
    const searchUrl = environment.serverUrl + `search/${keyword}`;

    return this.http.get<User[]>(searchUrl);
  }

  followUser(username: string) {
    const followUserUrl = environment.serverUrl + `follow/${username}`;

    return this.http.post<string>(followUserUrl, {});
  }

  unfollowUser(username: string) {
    const followUserUrl = environment.serverUrl + `unfollow/${username}`;

    return this.http.post<string>(followUserUrl, {});
  }

  editProfileDetails(formData: FormData) {
    const url = environment.serverUrl + 'edit-profile';
    return this.http.post<User>(url, formData);
  }

  changeUsername(usernameChangeDTO: UsernameChangeDTO) {
    const url = environment.serverUrl + "change-username";
    return this.http.post<UsernameChangeDTO>(url, usernameChangeDTO)
  }

  getMostFollowed() {
    const url = environment.serverUrl + "most-followed";
    return this.http.get<User[]>(url);
  }


}
