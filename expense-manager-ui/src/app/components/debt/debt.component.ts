import { Component, OnInit } from '@angular/core';
import { Debt } from 'src/app/model/Debt';
import { DebtService } from 'src/app/services/debt.service';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-debt',
  templateUrl: './debt.component.html',
  styleUrls: ['./debt.component.css']
})
export class DebtComponent implements OnInit {

  debts: Debt [];

  monthlyDebt: number = 0;
  dateMonthMap = {
    1: 'JAN', 2: 'FEB', 3: 'MAR', 4: 'APR', 5: 'MAY', 6: 'JUN', 7: 'JUL', 8: 'AUG', 9: 'SEP', 10: 'OCT',
    11: 'NOV', 12: 'DEC',
  }
  monthyear: string
  selectedYear: number
  selectedMonth: number

  constructor(private debtService: DebtService,
    private router: Router, private authService: AuthService, private alertService: AlertService) {
    if (!sessionStorage.getItem('id_token'))
      this.router.navigate(['/login'])
  }

  ngOnInit(): void {
    var d = new Date();
    var month = d.getUTCMonth() + 1; // Since getUTCMonth() returns month from 0-11 not 1-12
    var year = d.getUTCFullYear();
    console.log(month, year)
    this.selectedYear=year
    this.selectedMonth = this.dateMonthMap[month]
    this.getTotalDebt(this.dateMonthMap[month], year)
    this.getAllDebts(this.dateMonthMap[month], year)
  }


  getMonthAndYear() {
    console.log(this.monthyear)
    this.selectedMonth = Number(this.monthyear.split('-')[1])
    this.selectedYear = Number(this.monthyear.split('-')[0])
    console.log(this.selectedMonth, this.selectedYear)
    this.getTotalDebt(this.dateMonthMap[this.selectedMonth], this.selectedYear)
  }

  getTotalDebt(month, year) {
    this.getAllDebts(month, year)
    this.debtService.getTotalDebt(month, year)
      .then(res => (res.status !== 403 && res.status !== 401 && res.status !== 408) ? res.json() : this.authService.logout())
      .then(data => {
        this.monthlyDebt = data.total
      }).catch(err => console.log(err))
  }

  getAllDebts(month, year) {
    this.debtService.getAllDebts(month, year)
      .then(res => (res.status !== 403 && res.status !== 401 && res.status !== 408) ? res.json() : this.authService.logout())
      .then(data => {
        this.debts = data;
      }).catch(err => console.log(err))
  }

  deleterequest(id){
    if(confirm("Do you want to delete")){
      this.debtService.deleteDebt(id)
        .then(res => res.json())
        .then(data => {
          if(data.status){
            this.getAllDebts(this.selectedMonth, this.selectedYear);
            this.alertService.info('Deleted item successfully')
          }
          else{
            this.alertService.warning("Failed to delete item")
          }
        })
        .catch(err => console.log(err))
    }
  }

}
