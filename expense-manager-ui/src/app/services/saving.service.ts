import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class SavingService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  serverUrl = environment.baseUrl;


  getTotalSaving(month, year) {

    return fetch(this.serverUrl + '/api/moneymanager/savings/saving-total', {
      headers: this.authService.getHeaders(),
      method: 'POST', // GET, POST, PUT, DELETE
      body: JSON.stringify({ month, year })
    })
  }

  getAllSavings(month, year) {

    return fetch(`${this.serverUrl}/api/moneymanager/savings/${month}/${year}`, {
      headers: this.authService.getHeaders(),
      method: 'GET',// GET, POST, PUT, DELETE,
    })
  }

  deleteSaving(savingId) {

    return fetch(`${this.serverUrl}/api/moneymanager/savings/${savingId}`, {
      headers: this.authService.getHeaders(),
      method: 'DELETE'
    })
  }

}


