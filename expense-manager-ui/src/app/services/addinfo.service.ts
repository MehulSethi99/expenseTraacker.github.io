import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AddinfoService {

  constructor(private authService: AuthService) { }

  serverUrl = environment.baseUrl;

  /** Expense */

    //Add expense
  
  addExpense(title, description, date, amount, month, year){
    

    return fetch(`${this.serverUrl}/api/moneymanager/expense`, {
      headers: this.authService.getHeaders(),
      method: 'POST',
      body: JSON.stringify({
        "title": title,
        "description": description,
        "date": date,
        "amount": amount,
        "month": month,
        "year": year
      })
    });
  }


  addSaving(title, description, date, amount, month, year){

    return fetch(`${this.serverUrl}/api/moneymanager/savings`, {
      headers: this.authService.getHeaders(),
      method: 'POST',
      body: JSON.stringify({
        "title": title,
        "description": description,
        "date": date,
        "amount": amount,
        "month": month,
        "year": year
      })
    });
  }


  addLending(title, description, date, amount, month, year){

    return fetch(`${this.serverUrl}/api/moneymanager/lending`, {
      headers: this.authService.getHeaders(),
      method: 'POST',
      body: JSON.stringify({
        "title": title,
        "description": description,
        "date": date,
        "amount": amount,
        "month": month,
        "year": year
      })
    });
  }


  addDebt(title, description, date, amount, month, year){

    return fetch(`${this.serverUrl}/api/moneymanager/debt`, {
      headers: this.authService.getHeaders(),
      method: 'POST',
      body: JSON.stringify({
        "title": title,
        "description": description,
        "date": date,
        "amount": amount,
        "month": month,
        "year": year
      })
    });
  }

}
