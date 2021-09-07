import { Component, OnInit } from '@angular/core';
import { CarServiceService } from '../car-service.service';
import { Car } from '../cars/car';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  cars: Car[] = [];

  constructor(private carService: CarServiceService) { }

  ngOnInit() {
    this.getCars();
  }

  getCars(): void {
    this.carService.getAllCars()
      .subscribe(cars => this.cars = cars.slice(1,3));
  }

}
