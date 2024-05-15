import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  serverUrl = environment.baseUrl;

  getProfile(){
    return fetch(`${this.serverUrl}/api/moneymanager/profile`, {
      headers: this.authService.getHeaders(),
      method: 'GET'
    })
  }
}
