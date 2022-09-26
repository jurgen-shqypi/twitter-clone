import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { HotToastService } from '@ngneat/hot-toast';
import { PasswordChangeDTO } from 'src/app/common/password-change-dto';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  passwordChangeRequest: PasswordChangeDTO = new PasswordChangeDTO;
  passwordChangeFormGroup: FormGroup;
  constructor(public dialogRef: MatDialogRef<ChangePasswordComponent>,
              private toast: HotToastService,
              private authService: AuthService) {
    this.passwordChangeFormGroup = new FormGroup({
      oldPassword: new FormControl(''),
      newPassword: new FormControl('')
    })
  }

  ngOnInit(): void {
  }

  changePassword() {
    const oldPassword = document.getElementById("oldPassword") as HTMLInputElement;
    const newPasswordFirst = document.getElementById("newPasswordFirst") as HTMLInputElement;
    const newPasswordSecond = document.getElementById("newPasswordSecond") as HTMLInputElement;
    this.passwordChangeRequest.oldPassword = oldPassword.value;
    this.passwordChangeRequest.newPassword = newPasswordFirst.value;
    if(newPasswordFirst.value !== newPasswordSecond.value){
      this.toast.error("Passwords don't match");
      return;
    }

    this.authService.changePasswordRequest(this.passwordChangeRequest).subscribe(
      {
        next: response => {
          this.toast.success("Password changed!");
        },
        error: err => {
          this.toast.error(err.error);
        }
      }
    )
    
  }

}
