import { Component, OnInit } from '@angular/core';
import { Expense } from '../../model/Expense'
import { Router } from '@angular/router';
import { ExpenseService } from 'src/app/services/expense.service';
import { AuthService } from 'src/app/services/auth.service';
import { AlertService } from 'src/app/services/alert.service';

@Component({
  selector: 'app-expense',
  templateUrl: './expense.component.html',
  styleUrls: ['./expense.component.css']
})
export class ExpenseComponent implements OnInit {

  expenses: Expense[];
  monthlyExpense: number = 0;
  dateMonthMap = {
    1: 'JAN', 2: 'FEB', 3: 'MAR', 4: 'APR', 5: 'MAY', 6: 'JUN', 7: 'JUL', 8: 'AUG', 9: 'SEP', 10: 'OCT',
    11: 'NOV', 12: 'DEC',
  }
  monthyear: string
  selectedYear: number
  selectedMonth: number

  constructor(private expenseService: ExpenseService,
    private router: Router, private authService: AuthService, private alertService: AlertService) {
    if (!sessionStorage.getItem('id_token'))
      this.router.navigate(['/login'])
  }

  ngOnInit(): void {
    var d = new Date();
    var month = d.getUTCMonth() + 1; // Since getUTCMonth() returns month from 0-11 not 1-12
    var year = d.getUTCFullYear();
    console.log(month, year)
    this.selectedYear = year
    this.selectedMonth = this.dateMonthMap[month]
    this.getTotalExpense(this.dateMonthMap[month], year)
    this.getAllExpenses(this.dateMonthMap[month], year)
  }

  getMonthAndYear() {
    console.log(this.monthyear)
    this.selectedMonth = Number(this.monthyear.split('-')[1])
    this.selectedYear = Number(this.monthyear.split('-')[0])
    console.log(this.selectedMonth, this.selectedYear)
    this.getTotalExpense(this.dateMonthMap[this.selectedMonth], this.selectedYear)
  }

  getTotalExpense(month, year) {
    this.getAllExpenses(month, year)
    this.expenseService.getTotalExpense(month, year)
      .then(res => (res.status !== 403 && res.status !== 401 && res.status !== 408) ? res.json() : this.authService.logout())
      .then(data => {
        this.monthlyExpense = data.total
      }).catch(err => console.log(err))
  }

  getAllExpenses(month, year) {
    this.expenseService.getAllExpenses(month, year)
      .then(res => (res.status !== 403 && res.status !== 401 && res.status !== 408) ? res.json() : this.authService.logout())
      .then(data => {
        this.expenses = data;
      }).catch(err => console.log(err))
  }

  deleterequest(id) {
    if (confirm("Do you want to delete")) {
      this.expenseService.deleteExpense(id)
        .then(res => res.json())
        .then(data => {
          if (data.status) {
            this.getAllExpenses(this.selectedMonth, this.selectedYear);
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
