import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Car} from "../cars/car";

@Injectable({
  providedIn: 'root'
})
export class FileService {
  constructor(private http: HttpClient) {}
  baseApiUrl = "http://localhost:8080/"

  upload(file: File, car:Car): Observable<any> {
    // Create form data
    const formData = new FormData();
    // Store form name as "file" with file data
    formData.append("file", file, file.name);
    // Make http post request over api
    // with formData as req
    return this.http.post(`${this.baseApiUrl}api/files/uploadFile`, formData, {params:{"model":`${car.model}`,"brand":`${car.brand.brandName}`}});
  }
}
