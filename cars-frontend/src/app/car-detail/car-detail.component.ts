import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CarServiceService } from '../car-service.service';
import { Car } from '../cars/car';
import { Location } from '@angular/common';

@Component({
  selector: 'app-car-detail',
  templateUrl: './car-detail.component.html',
  styleUrls: ['./car-detail.component.css']
})
export class CarDetailComponent implements OnInit {
// @Input()car?:Car;
car:Car | undefined;
  constructor(private route: ActivatedRoute,
    private carService: CarServiceService,
    private location: Location) { }

  ngOnInit(): void {
    this.getCar();
  }

  getCar(): void {
    const id = this.route.snapshot.paramMap.get('id')!;
    this.carService.getCar(id)
      .subscribe(car => this.car = car);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.car) {
      this.carService.updateCar(this.car)
        .subscribe(() => this.goBack());
    }
  }
}
