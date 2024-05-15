import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { User } from 'src/app/model/User';
import { Router } from '@angular/router';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private profileService: ProfileService,
    private router: Router, private authService: AuthService) { 
      if (!sessionStorage.getItem('id_token'))
          this.router.navigate(['/login'])
    }

  user: User;

  ngOnInit(): void {
    this.getProfile()
  }

  getProfile(){
    this.profileService.getProfile()
      .then(res => (res.status !== 403 && res.status !== 401 && res.status !== 408) ? res.json() : this.authService.logout())
      .then((data)=>{
       this.user = data;
      })
  }
  logout(){
    this.authService.logout();
  }
}
