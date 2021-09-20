import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class FileService {
  constructor(private http: HttpClient) {}
  baseApiUrl = "http://localhost:8080/api/files/uploadFile"
  upload(file: File): Observable<any> {

    // Create form data
    const formData = new FormData();

    // Store form name as "file" with file data
    formData.append("file", file, file.name);

    // Make http post request over api
    // with formData as req
    return this.http.post(this.baseApiUrl, formData)
  }
  // httpOptions = {
  //   headers: new HttpHeaders({ 'Access-Control-Allow-Origin': 'http://localhost:4200' })
  // };
  // // setHeader('Access-Control-Allow-Origin', 'http://localhost:4200');
  // public upload(data: FormData): Observable<any> {
  //   return this.http.post(`${this.getApi()}/api/files/uploadFile`, data, this.httpOptions);
  // }
  //
  // private getApi(): string {
  //   return `${environment.apiBaseUrl}`;
  // }
}
