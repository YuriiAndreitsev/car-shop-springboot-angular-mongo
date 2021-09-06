import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Car } from './cars/car';
import { CARS } from './cars/mock-cars';

@Injectable({
  providedIn: 'root'
})
export class CarServiceService {
getAllCars():Observable<Car[]>{
  const cars = of(CARS);
  return cars;
}
  constructor() { }
}
