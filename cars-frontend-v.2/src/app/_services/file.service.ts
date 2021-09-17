import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FileService {
  constructor(private http: HttpClient) {}
  httpOptions = {
    headers: new HttpHeaders({ 'Access-Control-Allow-Origin': 'http://localhost:4200' })
  };
  // setHeader('Access-Control-Allow-Origin', 'http://localhost:4200');
  public upload(data: FormData): Observable<any> {
    return this.http.post(`${this.getApi()}/api/files/uploadFile`, data, this.httpOptions);
  }

  private getApi(): string {
    return `${environment.apiBaseUrl}`;
  }
}
