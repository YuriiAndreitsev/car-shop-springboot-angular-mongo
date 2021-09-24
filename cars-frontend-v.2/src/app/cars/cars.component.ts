import {Component, OnInit} from '@angular/core';
import {CarServiceService} from '../car-service.service';
import {Car} from './car';
import {Task} from '../payload/task';
import {TaskBrand} from "../payload/taskBrand";


@Component({
  selector: 'app-cars',
  templateUrl: './cars.component.html',
  styleUrls: ['./cars.component.css']
})
export class CarsComponent implements OnInit {
  constructor(private carService: CarServiceService) {

  }

  cars: Car[] = [];
  selectedCar?: Car;
  page: number = 1;
  pageSize: number = 6;
  brands: string[] = [];
  allComplete: boolean = false;
  task: Task = {
    name: 'Parameters',
    completed: false,
    color: 'primary',
    subtasks: [
      {name: 'Model', completed: false, color: 'primary'},
      {name: 'Brand', completed: false, color: 'accent'},
      {name: 'Price', completed: false, color: 'warn'}
    ]
  };

  taskBrand: TaskBrand = new TaskBrand("Brand", false, "primary", this.brands);
  allBrandsComplete: boolean = false;

  someBrandComplete(): boolean {
    if (this.taskBrand.subtasks == null) {
      return false;
    }
    // this.sendCarUniquelizeParams();
    return this.task.subtasks.filter(t => t.completed).length > 0 && !this.allComplete;
  }

  setBrandAll(completed: boolean) {
    this.allBrandsComplete = completed;
    if (this.taskBrand.subtasks == null) {
      return;
    }
    this.taskBrand.subtasks.forEach(t => t.completed = completed);
  }

  updateAllBrandComplete() {
    this.allBrandsComplete = this.taskBrand.subtasks != null && this.taskBrand.subtasks.every(t => t.completed);
    this.sendCarUniquelizeParams();
  }

  sendCarUniquelizeParams(): void {
    this.carService.sendTask(this.task).subscribe(cars => this.cars = cars);
    ;
  }

  updateAllComplete() {
    this.allComplete = this.task.subtasks != null && this.task.subtasks.every(t => t.completed);
    this.sendCarUniquelizeParams();
  }

  someComplete(): boolean {
    if (this.task.subtasks == null) {
      return false;
    }
    // this.sendCarUniquelizeParams();
    return this.task.subtasks.filter(t => t.completed).length > 0 && !this.allComplete;
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.task.subtasks == null) {
      return;
    }
    this.task.subtasks.forEach(t => t.completed = completed);
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
    this.getBrands();
    this.getCars();
  }

  delete(car: Car): void {
    this.cars = this.cars.filter(c => c !== car);
    this.carService.deleteCar(car.id).subscribe();
  }
}
