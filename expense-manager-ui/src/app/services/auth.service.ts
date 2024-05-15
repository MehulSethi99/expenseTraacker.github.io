import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  authToken: any;
  user: any;

  constructor(private router: Router) { }

  serverUrl = environment.baseUrl;

  loginUser(email, password) {

    let data = { email, password }
    return fetch(`${this.serverUrl}/api/users/login`, {
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(data),
    })
  }

  googleAuthLogin(email, password) {

    let data = { email, password }
    return fetch(`${this.serverUrl}/api/users/google-auth-login`, {
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(data),
    })
  }

  registerUser(email, password, firstname, lastname, avatar, preferedcurrency){
    let data={
      "firstName": firstname,
      "lastName": lastname,
      "email": email,
      "avatarUrl": avatar,
      "password": password,
      "preferedCurrency": preferedcurrency
    }
    return fetch(`${this.serverUrl}/api/users/register`, {
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      },
      method: 'POST',
      body: JSON.stringify(data)
    })
  }

  storeUserData(token) {
    sessionStorage.setItem('id_token', token);
    this.authToken = token;
  }

  getHeaders() {
    return {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Authorization': `Bearer ${sessionStorage.getItem('id_token')}`,
      'Access-Control-Request-Headers': '*',
      'Access-Control-Allow-Headers': '*'
    }
  }

  logout() {
    sessionStorage.removeItem('id_token')
    this.router.navigate(['/login'])
  }
}
