import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as moment from 'moment';
import { Signup } from 'src/app/common/signup';
import { AuthService } from 'src/app/services/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupFormGroup: FormGroup;
  signupRequest: Signup;
  minDate: Date = new Date()
  constructor(private authService: AuthService) {
    this.signupFormGroup = new FormGroup({
      email: new FormControl('', Validators.required),
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      name: new FormControl('', Validators.required),
      dateOfBirth: new FormControl(new Date(), Validators.required)
    });
    this.signupRequest = {
      email: '',
      username: '',
      password: '',
      name: '', 
      dateOfBirth: new Date(),
      headerPicture: environment.defaultHeaderPicture,
      profilePicture: environment.defaultProfilePicture
    }
    this.minDate.setFullYear(new Date().getFullYear() - 15);
   }

  ngOnInit(): void {
  }

  signup() {
    this.signupRequest.email = this.signupFormGroup.get('email')?.value;
    this.signupRequest.username = this.signupFormGroup.get('username')?.value;
    this.signupRequest.password = this.signupFormGroup.get('password')?.value;
    this.signupRequest.name = this.signupFormGroup.get('name')?.value;
    this.signupRequest.dateOfBirth = this.signupFormGroup.get('dateOfBirth')?.value;
    console.log(this.signupRequest);
    this.authService.signup(this.signupRequest);
  }

}
