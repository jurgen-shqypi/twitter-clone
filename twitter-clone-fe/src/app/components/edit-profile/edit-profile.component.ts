import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { User } from 'src/app/common/user';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { EditProfile } from 'src/app/common/edit-profile';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  user: User;
  todayDate = new Date();
  editProfileFormGroup: FormGroup;
  headerPhotoFile: any;
  profilePhotoFile: any;
  editRequest: EditProfile = new EditProfile;
  constructor(public location: Location,
              private userService: UserService,
              private authService: AuthService,
              private router: Router) {
    this.user = JSON.parse(localStorage.getItem('user')!);
    this.editProfileFormGroup = new FormGroup({
      'headerPhoto': new FormControl(this.user.headerPicture, Validators.required),
      'profilePhoto':  new FormControl(this.user.profilePicture, Validators.required),
      'name':  new FormControl(this.user.name, Validators.required),
      'description':  new FormControl(this.user.description, Validators.required),
      'location':  new FormControl(this.user.location, Validators.required),
      'dateOfBirth':  new FormControl(new Date(this.user.dateOfBirth).toISOString().substring(0,10), Validators.required)
    })
    this.todayDate.setFullYear(new Date().getFullYear() - 15);
   }

  ngOnInit(): void {

  }

  goBack() {
    this.location.back();
  }

  onSubmit() {
    const formData = new FormData()
    this.editRequest.name = this.editProfileFormGroup.get('name')?.value;
    this.editRequest.description = this.editProfileFormGroup.get('description')?.value;
    this.editRequest.location = this.editProfileFormGroup.get('location')?.value;
    this.editRequest.dateOfBirth = this.editProfileFormGroup.get('dateOfBirth')?.value;
    this.editRequest.profilePicture = this.editProfileFormGroup.get('profilePhoto')?.value;
    this.editRequest.headerPicture = this.editProfileFormGroup.get('headerPhoto')?.value;
    formData.append('editRequestDTO', new Blob([JSON
      .stringify(this.editRequest)], {
      type: 'application/json'
    }));
    if(this.headerPhotoFile != null){
      formData.append("headerPicture", this.headerPhotoFile);
    }
    if(this.profilePhotoFile != null){
      formData.append("profilePicture", this.profilePhotoFile);
    }

    this.userService.editProfileDetails(formData).subscribe(
      data => {
        this.authService.setUser(data);
        this.router.navigateByUrl(`user/${this.user.username}`)
      }
    )

  }

  changePicture(id: string, input: any) {
    let element = document.getElementById(id) as HTMLImageElement;
    if(id === "headerPhoto"){
      element.src = URL.createObjectURL(input.files[0])
      this.headerPhotoFile = input.files[0];
    } else {
      element.src = URL.createObjectURL(input.files[0]);
      this.profilePhotoFile = input.files[0]
    }
  }
}
