import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { User } from 'src/app/common/user';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { ComposeTweetComponent } from '../compose-tweet/compose-tweet.component';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrls: ['./banner.component.css']
})
export class BannerComponent implements OnInit {

  loggedInUser: User = new User;

  constructor(private authService: AuthService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
      this.updateUser()
  }

  logout() {
    this.authService.logout();
  }

  updateUser() {
    this.authService.userSubject.subscribe(
      data => {
        if(data === ''){
          this.loggedInUser = new User;
        } else {
          this.loggedInUser = JSON.parse(data);
        }
        
      }
    )
  }

  openTweetDialog() {
    this.dialog.open(ComposeTweetComponent, {
      panelClass: 'panel'
    });
  }

}
