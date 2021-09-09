import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

//const AUTH_API = 'http://localhost:8080/api/auth/';
const AUTH_API = 'http://localhost:8080/api/login';

//application/x-www-form-urlencoded

const httpOptions = {
  // headers: new HttpHeaders({ 'Content-Type': 'text/plain'})
 headers: new HttpHeaders({ 'Content-Type': 'application/json' })
 // headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' })
};
// const header = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' });

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(
      "http://localhost:8080/api/login",
      null,
      {headers:{ 'Content-Type': 'application/x-www-form-urlencoded' },
              params:{"username":username,"password":password}}
      );
  }

  // login(username: string, password: string): Observable<any> {
  //   //return this.http.post(AUTH_API + 'signin', {
  //   return this.http.post(AUTH_API, {
	// 	"username":username,
  //     "password":password
  //   }, httpOptions);
  // }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      username,
      email,
      password
    }, httpOptions);
  }
}
