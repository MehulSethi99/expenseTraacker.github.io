import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  password: string;
  email: string;
  googleauthcode: string;
  isPasswordChoice: boolean = false;


  constructor(private authService: AuthService,
    private router: Router, private alertService: AlertService
  ) {
    if (sessionStorage.getItem('id_token'))
      this.router.navigate(['/expense'])
  }

  ngOnInit(): void {
  }


  onLoginSubmit() {
    if (!this.isPasswordChoice) {
      this.authService.googleAuthLogin(this.email, this.googleauthcode)
        .then(res => res.json())
        .then(data => {
          if (data.token) {
            this.authService.storeUserData(data.token)
            this.router.navigate(['/expense'])
          }
          else {
            this.alertService.warning("Invalid user name and password");
            this.router.navigate(['/login'])
          }
        }).catch(err => console.log(err))
    }
    else {
      this.authService.loginUser(this.email, this.password)
        .then(res => res.json())
        .then(data => {
          if (data.token) {
            this.authService.storeUserData(data.token)
            this.router.navigate(['/expense'])
          }
          else {
            this.alertService.warning("Invalid user name and password");
            this.router.navigate(['/login'])
          }
        }).catch(err => console.log(err))
    }

  }
}
