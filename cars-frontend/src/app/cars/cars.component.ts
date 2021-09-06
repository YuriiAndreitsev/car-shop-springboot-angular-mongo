import { Component, OnInit } from '@angular/core';
import { CarServiceService } from '../car-service.service';
import { MessageService } from '../message.service';
import { Car } from './car';

@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit {
  cars:Car[]=[];
  selectedCar?: Car;

  constructor(private carService:CarServiceService,private messageService: MessageService) { }

  getCars():void{
    this.carService.getAllCars().subscribe(cars=>this.cars=cars);
  }

  onSelect(car: Car): void {
    this.selectedCar = car;
    this.messageService.add(`CarsComponent: Selected car id=${car.id}`);
  }

  ngOnInit(): void {
    this.getCars();
  }

}
