import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { User } from 'src/app/common/user';

@Component({
  selector: 'app-user-liked-list',
  templateUrl: './user-liked-list.component.html',
  styleUrls: ['./user-liked-list.component.css']
})
export class UserLikedListComponent implements OnInit {

  usersWhoLiked: User[] = [];

  constructor(public dialogRef: MatDialogRef<UserLikedListComponent>,
              @Inject(MAT_DIALOG_DATA) public data: { usersData: User[] }) {
    this.usersWhoLiked = data.usersData;
  }

  ngOnInit(): void {
    console.log(this.usersWhoLiked)
  }

}
