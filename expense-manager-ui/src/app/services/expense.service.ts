import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';


@Injectable({
  providedIn: 'root'
})

export class ExpenseService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  serverUrl = environment.baseUrl;


  getTotalExpense(month, year) {


    return fetch(this.serverUrl+'/api/moneymanager/expense/expense-total', {
      headers: this.authService.getHeaders(),
      method: 'POST', // GET, POST, PUT, DELETE
      body: JSON.stringify({ month, year })
    })
  }


  getAllExpenses(month, year) {

    return fetch(`${this.serverUrl}/api/moneymanager/expense/${month}/${year}`, {
      headers: this.authService.getHeaders(),
      method: 'GET',// GET, POST, PUT, DELETE,
    })
  }

  deleteExpense(expenseID){
    return fetch(`${this.serverUrl}/api/moneymanager/expense/${expenseID}`, {
      headers: this.authService.getHeaders(),
      method: 'DELETE'
    })
  }

}
