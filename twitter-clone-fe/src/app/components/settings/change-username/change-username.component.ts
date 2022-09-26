import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { HotToastService } from '@ngneat/hot-toast';
import { UsernameChangeDTO } from 'src/app/common/username-change-dto';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-change-username',
  templateUrl: './change-username.component.html',
  styleUrls: ['./change-username.component.css']
})
export class ChangeUsernameComponent implements OnInit {
  usernameChangeDTO: UsernameChangeDTO = new UsernameChangeDTO;
  constructor(public dialogRef: MatDialogRef<ChangeUsernameComponent>,
              private userService: UserService,
              private toast: HotToastService,
              private authService: AuthService) { }

  ngOnInit(): void {
  }

  changeUsername() {
    let username = document.getElementById('username') as HTMLInputElement;
    this.usernameChangeDTO.username = username.value;
    if(this.usernameChangeDTO.username.length < 4){
      this.toast.error("Username too short!");
      return;
    }
    this.userService.changeUsername(this.usernameChangeDTO).subscribe(
      {
        next: response => {
          this.toast.success("Username changed!");
          this.authService.getUserDetails();
        }, 
        error: err => {
          this.toast.error(err.error);
        }
      }
    )
  }

}
