import {Component, OnInit} from '@angular/core';
import {Car} from "../cars/car";
import {CarServiceService} from "../car-service.service";
import {environment} from "../../environments/environment";
import {FileService} from "../_services/file.service";

@Component({
  selector: 'app-add-car',
  templateUrl: './add-car.component.html',
  styleUrls: ['./add-car.component.css']
})
export class AddCarComponent  {
  // public fileName = '';
  // public findFile = '';
  // public alert: {
  //   value: string;
  //   type: 'success' | 'danger';
  // };
  car: Car = new Car("", "", 0);

  constructor(private carService: CarServiceService, private fileService: FileService) {
  }


  add(car: Car): void {
    console.log("car to add")
    console.log(car);
    this.carService.addCar(car)
      .subscribe(car => {      });
  }

  addCar(): void {
    console.log("car to add")
    console.log(this.car);
    this.carService.addCar(this.car)
      .subscribe(car => {      });
  }

  shortLink: string = "";
  loading: boolean = false; // Flag variable
  file: File | undefined; // Variable to store file
  // On file Select
  // @ts-ignore
  onChange(event) {
    this.file = event.target.files[0];
  }

  // OnClick of button Upload
  onUpload() {
    this.loading = !this.loading;
    console.log(this.file);
    // @ts-ignore

    this.fileService.upload(this.file, this.car).subscribe(
      (event: any) => {
        if (typeof (event) === 'object') {
          // Short link via api response
          this.shortLink = event.link;
          this.loading = false; // Flag variable
        }
      }
    );
  }
}
