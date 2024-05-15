import { Component, OnInit } from '@angular/core';
import { Lending } from 'src/app/model/Lending';
import { LendingService } from 'src/app/services/lending.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-lending',
  templateUrl: './lending.component.html',
  styleUrls: ['./lending.component.css']
})
export class LendingComponent implements OnInit {


  lendings: Lending[];

  monthlyLending: number = 0;
  dateMonthMap = {
    1: 'JAN', 2: 'FEB', 3: 'MAR', 4: 'APR', 5: 'MAY', 6: 'JUN', 7: 'JUL', 8: 'AUG', 9: 'SEP', 10: 'OCT',
    11: 'NOV', 12: 'DEC',
  }
  monthyear: string
  selectedYear: number
  selectedMonth: number

  constructor(private lendingService: LendingService,
    private router: Router, private authService: AuthService, private alertService: AlertService) {
    if (!sessionStorage.getItem('id_token'))
      this.router.navigate(['/login'])
  }

  ngOnInit(): void {
    var d = new Date();
    var month = d.getUTCMonth() + 1; // Since getUTCMonth() returns month from 0-11 not 1-12
    var year = d.getUTCFullYear();
    console.log(month, year)
    this.selectedMonth = this.dateMonthMap[month]
    this.selectedYear = year
    this.getTotalLending(this.dateMonthMap[month], year)
    this.getAllLendings(this.dateMonthMap[month], year)
  }


  getMonthAndYear() {
    console.log(this.monthyear)
    this.selectedMonth = Number(this.monthyear.split('-')[1])
    this.selectedYear = Number(this.monthyear.split('-')[0])
    console.log(this.selectedMonth, this.selectedYear)
    this.getTotalLending(this.dateMonthMap[this.selectedMonth], this.selectedYear)
  }

  getTotalLending(month, year) {
    this.getAllLendings(month, year)
    this.lendingService.getTotalLending(month, year)
      .then(res => (res.status !== 403 && res.status !== 401 && res.status !== 408) ? res.json() : this.authService.logout())
      .then(data => {
        this.monthlyLending = data.total
      }).catch(err => console.log(err))
  }

  getAllLendings(month, year) {
    this.lendingService.getAllLendings(month, year)
      .then(res => (res.status !== 403 && res.status !== 401 && res.status !== 408) ? res.json() : this.authService.logout())
      .then(data => {
        this.lendings = data;
      }).catch(err => console.log(err))
  }

  deleterequest(id) {
    if (confirm("Do you want to delete")) {
      this.lendingService.deleteLending(id)
        .then(res => res.json())
        .then(data => {
          if (data.status) {
            this.getAllLendings(this.selectedMonth, this.selectedYear);
            this.alertService.info('Deleted item successfully')
          }
          else {
            this.alertService.warning("Failed to delete item")
          }
        })
        .catch(err => console.log(err))
    }
  }

}
