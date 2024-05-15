import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class LendingService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  serverUrl = environment.baseUrl;


  getTotalLending(month, year) {


    return fetch(this.serverUrl+'/api/moneymanager/lending/lend-total', {
      headers: this.authService.getHeaders(),
      method: 'POST', // GET, POST, PUT, DELETE
      body: JSON.stringify({ month, year })
    })
  }

  getAllLendings(month, year) {

    return fetch(`${this.serverUrl}/api/moneymanager/lending/${month}/${year}`, {
      headers: this.authService.getHeaders(),
      method: 'GET',// GET, POST, PUT, DELETE,
    })
  }

  deleteLending(lendingID){
    return fetch(`${this.serverUrl}/api/moneymanager/lending/${lendingID}`, {
      headers: this.authService.getHeaders(),
      method: 'DELETE'
    })
  }

}
