import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AddinfoService } from '../../services/addinfo.service'
import { AuthService } from 'src/app/services/auth.service';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-addnewdata',
  templateUrl: './addnewdata.component.html',
  styleUrls: ['./addnewdata.component.css']
})
export class AddnewdataComponent implements OnInit {

  constructor(private addinfoService: AddinfoService,
    private router: Router, private authService: AuthService, private alertService: AlertService) { 
      if (!sessionStorage.getItem('id_token'))
        this.router.navigate(['/login'])
    }

  month: string;
  year: number;
  type: string;
  amount: number ;
  title: string = "";
  description: string = "";
  date: number;
  selectedType: string = "Choose Type";

  dateMonthMap = {
    1: 'JAN', 2: 'FEB', 3: 'MAR', 4: 'APR', 5: 'MAY', 6: 'JUN', 7: 'JUL', 8: 'AUG', 9: 'SEP', 10: 'OCT',
    11: 'NOV', 12: 'DEC',
  }

  types = [
    { value: 'expense', viewValue: 'Expense' },
    { value: 'saving', viewValue: 'Saving' },
    { value: 'debt', viewValue: 'Debt' },
    { value: 'lending', viewValue: 'Lending' }
  ]

  ngOnInit(): void {
    var d = new Date();
    this.month = this.dateMonthMap[d.getUTCMonth() + 1];
    this.year = d.getUTCFullYear();
    this.date = Date.now();
  }


  logout() {
    sessionStorage.removeItem('id_token')
    this.router.navigate(['/login'])
  }

  submitData() {
    if (this.selectedType != "" && this.selectedType != "Choose Type" && this.amount != undefined && this.title != "" && this.description != "") {
      if (this.selectedType == "expense") {
        this.addinfoService.addExpense(this.title, this.description, this.date ,this.amount, this.month, this.year)
          .then(res => {
            if(res.status !== 403 && res.status !== 401 && res.status !== 408 && res.status!==400){
              res.json();
              this.alertService.success("Expense added");
            } else {
              this.alertService.warning("Check your internet connection");
              this.authService.logout();
            }
          })
          .then(data => this.alertService.success("Expense added"))
          .catch(err => console.log(err))
      }
      else if (this.selectedType == "saving") {
        this.addinfoService.addSaving(this.title, this.description, this.date ,this.amount, this.month, this.year)
          .then(res => {
            if(res.status !== 403 && res.status !== 401 && res.status !== 408 && res.status!==400){
              res.json();
              this.alertService.success("Saving added");
            } else {
              this.alertService.warning("Check your internet connection");
              this.authService.logout();
            }
          })
          .then(data => console.log(data))
          .catch(err => console.log(err))
      }
      else if (this.selectedType == "debt") {
        this.addinfoService.addDebt(this.title, this.description, this.date ,this.amount, this.month, this.year)
        .then(res => {
          if(res.status !== 403 && res.status !== 401 && res.status !== 408 && res.status!==400){
            res.json();
            this.alertService.success("Debt added");
          } else {
            this.alertService.warning("Check your internet connection");
            this.authService.logout();
          }
        })
        .then(data => console.log(data))
        .catch(err => console.log(err))
      }
      else {
        this.addinfoService.addLending(this.title, this.description, this.date ,this.amount, this.month, this.year)
        .then(res => {
          if(res.status !== 403 && res.status !== 401 && res.status !== 408 && res.status!==400){
            res.json();
            this.alertService.success("Lending added");
          } else {
            this.alertService.warning("Check your internet connection");
            this.authService.logout();
          }
        })
        .then(data => console.log(data))
        .catch(err => console.log(err))
      }
    }
  }

}
