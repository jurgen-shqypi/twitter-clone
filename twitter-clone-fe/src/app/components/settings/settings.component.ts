import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ChangeUsernameComponent } from './change-username/change-username.component';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  ngOnInit(): void {
  }

  changeUsername() {
    this.dialog.open(ChangeUsernameComponent, {
      panelClass: 'panel'
    });
  }

  changePassword() {
    this.dialog.open(ChangePasswordComponent, {
      panelClass: 'panel'
    });
  }

}
