import {Component, OnInit} from '@angular/core';
import {CarServiceService} from '../car-service.service';
import {Car} from './car';
import {saveAs} from 'file-saver'
import {from} from "rxjs";

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
  imageFile: Blob;
  reader = new FileReader();
  private url: string | ArrayBuffer;
  image = new Image();

  constructor(private carService: CarServiceService) {
  }


  download(): void {
    this.carService.downloadFile().subscribe(data => this.imageFile = data);
    this.reader.readAsDataURL(this.imageFile); //FileStream response from .NET core backend
    this.reader.onload = _event => {
      this.url = this.reader.result; //url declared earlier
    };
    console.log(this.imageFile);
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
    this.download();
  }

  delete(car: Car): void {
    this.cars = this.cars.filter(c => c !== car);
    this.carService.deleteCar(car.id).subscribe();
  }
}
