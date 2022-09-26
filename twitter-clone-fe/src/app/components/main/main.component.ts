import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {

  storage: Storage = localStorage;
  isLoggedIn: string = 'false';

  constructor(private authService: AuthService) {
    if(this.storage.getItem('isLoggedIn') == null){
      this.storage.setItem('isLoggedIn', 'false');
    }
    
  }
  
  ngOnInit() {
    this.authService.loggedIn.subscribe({
      next:value => {
        this.isLoggedIn=value;
      },
      error:err => {
        console.log(err);
      }
    })
    
  }

}
