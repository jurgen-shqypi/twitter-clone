import { HttpClient } from '@angular/common/http';
import { TokenDTO } from '../common/token-dto';
import { User } from '../common/user';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Login } from '../common/login';
import { map, tap } from 'rxjs/operators';
import { Signup } from '../common/signup';
import { Router } from '@angular/router';
import { HotToastService } from '@ngneat/hot-toast';
import { PasswordChangeDTO } from '../common/password-change-dto';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  tokenResponse: TokenDTO;
  loggedIn: Subject<string> = new BehaviorSubject<string>(localStorage.getItem('isLoggedIn')!);
  userSubject: Subject<string> = new BehaviorSubject<string>(localStorage.getItem('user')!);
  constructor(private httpClient: HttpClient,
              private router: Router,
              private toast: HotToastService) {
    this.tokenResponse = {
      accessToken: '',
      refreshToken: ''
    }
   }

  login(login: Login) {
    const loginUrl = environment.serverUrl + "auth/login";
    
    this.httpClient.post<TokenDTO>(loginUrl, login).subscribe(
      {
        next: response => {
          this.tokenResponse = response;
          localStorage.setItem('isLoggedIn', 'true');
          this.loggedIn.next(localStorage.getItem('isLoggedIn')!);
          this.setTokens(this.tokenResponse);
          this.getUserDetails().subscribe({
            next: response => {
              localStorage.setItem("user", JSON.stringify(response));
              this.userSubject.next(localStorage.getItem('user')!);
              this.router.navigateByUrl('')
            }, 
            error: err => {
              console.log(`There was an error: ${err.message}`)
            }
          });
        },
        error: err => {
          console.log(err)
          this.toast.error("Credentials wrong!")
        }
      }
    );
  }

  getUserDetails() {
    const userInfoUrl = environment.serverUrl + "current/user";
    return this.httpClient.get<User>(userInfoUrl);
    
  }

  signup(signup: Signup) {
    const signupUrl = environment.serverUrl + 'auth/signup';
    this.httpClient.post<TokenDTO>(signupUrl, signup).subscribe({
      next:response => {
        console.log(response);
        this.tokenResponse = response;
        localStorage.setItem('isLoggedIn', 'true');
        this.loggedIn.next(localStorage.getItem('isLoggedIn')!);
        this.setTokens(this.tokenResponse);
        this.getUserDetails().subscribe({
          next: response => {
            localStorage.setItem("user", JSON.stringify(response));
            this.userSubject.next(localStorage.getItem('user')!);
            this.router.navigateByUrl('')
          }, 
          error: err => {
            console.log(`There was an error: ${err.message}`)
          }
        });
      }
      , error: err => {
        this.toast.error(err.error);
        console.log(err)
      }
    });
  }

  logout () {
    this.removeTokens()
    this.removeUser()
    localStorage.setItem('isLoggedIn', 'false');
    this.loggedIn.next(localStorage.getItem('isLoggedIn')!);
    this.router.navigateByUrl('');
  }

  refreshToken() {
    const refreshTokenLink = environment.serverUrl + 'auth/token';
    const tokenDTO: TokenDTO = {
      refreshToken: this.getRefreshToken()!,
      accessToken: ""
    }
    return this.httpClient.post<TokenDTO>(refreshTokenLink,
      tokenDTO)
      .pipe(tap(response => {
        console.log("refresh functions")
        this.removeTokens();
        this.setTokens(response);
      }))
  }

  getLoginStatus() {
    return localStorage.getItem("isLoggedIn");
  }

  setTokens(tokenDTO: TokenDTO) {
    localStorage.setItem("access_token", tokenDTO.accessToken);
    localStorage.setItem("refresh_token", tokenDTO.refreshToken);
  }

  getAccessToken() {
    return localStorage.getItem("access_token");
  }

  getRefreshToken() {
    return localStorage.getItem("refresh_token");
  }

  setUser(userDTO: User) {
    localStorage.setItem("user", JSON.stringify(userDTO));
  }

  removeTokens() {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
  }

  removeUser() {
    localStorage.removeItem("user");
    this.userSubject.next('');
  }

  changePasswordRequest(passwordChangeDTO: PasswordChangeDTO) {
    const url: string = environment.serverUrl + 'auth/change-password';
    console.log(passwordChangeDTO)
    return this.httpClient.post<PasswordChangeDTO>(url, passwordChangeDTO);
  }
}
