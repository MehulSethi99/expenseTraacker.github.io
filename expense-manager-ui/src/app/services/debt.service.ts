import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';


@Injectable({
  providedIn: 'root'
})
export class DebtService {
  constructor(private http: HttpClient, private authService: AuthService) { }

  serverUrl = environment.baseUrl;

  getTotalDebt(month, year) {

    return fetch(this.serverUrl+'/api/moneymanager/debt/debt-total', {
      headers: this.authService.getHeaders(),
      method: 'POST', // GET, POST, PUT, DELETE
      body: JSON.stringify({ month, year })
    })
  }

  getAllDebts(month, year) {

    return fetch(`${this.serverUrl}/api/moneymanager/debt/${month}/${year}`, {
      headers: this.authService.getHeaders(),
      method: 'GET',// GET, POST, PUT, DELETE,
    })
  }

  deleteDebt(expenseID){
    return fetch(`${this.serverUrl}/api/moneymanager/debt/${expenseID}`, {
      headers: this.authService.getHeaders(),
      method: 'DELETE'
    })
  }
}
