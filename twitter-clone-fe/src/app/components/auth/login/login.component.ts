import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Login } from 'src/app/common/login';
import { AuthService } from 'src/app/services/auth.service';
import { TokenDTO } from 'src/app/common/token-dto';
import { Observable, Subject } from 'rxjs';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginFormGroup: FormGroup;
  loginRequest: Login;
  constructor(private authService: AuthService,
              ) {
    this.loginFormGroup = new FormGroup({
      username: new FormControl(''),
      password: new FormControl('')
    });
    this.loginRequest = {
      username: '',
      password: ''
    };
    
   }

  ngOnInit(): void {
    
  }

  login() {
    this.loginRequest.username = this.loginFormGroup.get('username')?.value;
    this.loginRequest.password = this.loginFormGroup.get('password')?.value;
    this.authService.login(this.loginRequest);
  }

  loginAsTest(){
    this.loginFormGroup.setValue({
      username: 'test',
      password: 'test123'
    });
    this.login();
  }

}
