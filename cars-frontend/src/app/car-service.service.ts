import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Car } from './cars/car';
import { CARS } from './cars/mock-cars';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CarServiceService {
  
  private apiServerUrl = environment.apiBaseUrl;
  private getAllCarsURL = 'http://localhost:8080/api/cars/all';
//   getAllCars():Observable<Car[]>{
//     const cars = of(CARS);
//     this.messageService.add('CarService: fetched cars');
//     return cars;
// }

getAllCars(): Observable<Car[]> {
  return this.http.get<Car[]>(`${this.apiServerUrl}/api/cars/all`)
}


getCar(id: number): Observable<Car> {
  // For now, assume that a hero with the specified `id` always exists.
  // Error handling will be added in the next step of the tutorial.
  const car = CARS.find(c => c.id === id)!;
  this.messageService.add(`CarService: fetched car id=${id}`);
  return of(car);
}
  constructor(  private http: HttpClient,private messageService: MessageService) { }
}