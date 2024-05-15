import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  password: string;
  passwordconfirm: string;
  firstname: string;
  lastname: string;
  email: string;
  avatar: string = "https://image.flaticon.com/icons/svg/3135/3135715.svg";
  preferredCurrency: string = "INR";
  isformSubmitted: boolean = false;
  passwordMissmatch: boolean = false;
  passwordlengthIsValid: boolean = true;

  constructor(private authService: AuthService,
    private router: Router, private alertService: AlertService
  ) {
    if (sessionStorage.getItem('id_token'))
      this.router.navigate(['/expense'])
  }

  ngOnInit(): void {
  }

  onRegisterSubmit() {
    console.log(this.firstname, this.lastname, this.password, this.passwordconfirm, this.email, this.avatar)
    if (this.firstname != undefined && this.firstname.length > 3 && this.lastname != undefined && this.password != undefined && this.passwordconfirm != undefined
      && this.email != undefined && this.avatar != undefined) {
      if (this.password != this.passwordconfirm) {
        this.passwordMissmatch = true;
      } else if (this.password.length < 6) {
        this.passwordlengthIsValid = false
      } else {
        this.isformSubmitted = true;
        this.authService.registerUser(this.email, this.password, this.firstname, this.lastname, this.avatar, this.preferredCurrency)
          .then(res => (res.status !== 403 && res.status !== 401 && res.status !== 408) ? res.json() : console.log("Registration failed"))
          .then(data => data.token ? this.alertService.info("Complete registration by clicking hte link sent to your mail"): this.alertService.warning("Could not create account"))
          .catch(err => console.log(err))
      }
    }
  }

}
