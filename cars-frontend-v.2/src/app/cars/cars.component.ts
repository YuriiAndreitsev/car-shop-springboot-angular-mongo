import {Component, OnInit} from '@angular/core';
import {CarServiceService} from '../car-service.service';
import {Car} from './car';
import {FileService} from "../_services/file.service";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit {
  cars: Car[] = [];
  selectedCar?: Car;
  page: number = 1;
  pageSize: number = 6;
  brands: string[] = [];


  constructor(private carService: CarServiceService) {
  }


  getCars(): void {
    this.carService.getAllCars().subscribe(cars => this.cars = cars);
  }

  getCarsByBrand(brand: string): void {
    this.carService.getAllCarsByBrand(brand).subscribe(carsByBrand => this.cars = carsByBrand);
  }

  getBrands(): void {
    this.carService.getBrands().subscribe(brands => this.brands = brands);
  }

  onSelect(car: Car): void {
    this.selectedCar = car;
  }

  ngOnInit(): void {
    this.getCars();
    this.getBrands();
  }

  delete(car: Car): void {
    this.cars = this.cars.filter(c => c !== car);
    this.carService.deleteCar(car.id).subscribe();
  }
}
