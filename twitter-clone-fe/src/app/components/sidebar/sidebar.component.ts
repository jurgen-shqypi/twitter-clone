import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/common/user';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  userResults: User[] = []
  mostFollowedUsersResults: User[] = []
  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getMostFollowed();
  }

  searchUser(target: any) {
    let keyword = target.value
    if(keyword.length < 3){
      if (this.userResults.length > 0) {
        this.userResults = []
      }
      return;
    }
    this.userService.searchUser(keyword).subscribe(
      data => {
        this.userResults = data;
        console.log(this.userResults);
      }
    )
  }

  getMostFollowed() {
    this.userService.getMostFollowed().subscribe(
      data => {
        console.log(data)
        this.mostFollowedUsersResults = data;
      }
    )
  }

}
