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
export class AddCarComponent implements OnInit {
  public fileName = '';
  public findFile = '';
  public alert: {
    value: string;
    type: 'success' | 'danger';
  };
  car: Car = new Car("", "", 0);

  constructor(private carService: CarServiceService, private fileService: FileService) {
  }

  submitted = false;

  onSubmit() {
    this.submitted = true;
    this.add(this.car)
  }

  ngOnInit(): void {

  }


  add(car: Car): void {
    this.carService.addCar(car)
      .subscribe(car => {
        // this.carComponent.cars.push(car);
      });
  }


  public upload(event) {
    const file = event.target.files[0];

    if (this.isEmpty(file)) {
      this.alert = {value: `File not selected`, type: 'danger'};
      return;
    }

    const formData = new FormData();
    formData.append('file', file);
    formData.append('fileName', this.fileName);
    this.fileService.upload(formData).subscribe(
      (res) => {
        this.alert = {
          value: `Successfully uploaded the file ${res.value}`,
          type: 'success',
        };
      },
      (error) => {
        console.error(error);
        this.alert = {value: `Failed to upload file`, type: 'danger'};
      }
    );
  }

  public openFile(): void {
    const link = document.createElement('a');
    link.target = '_blank';
    link.href = `${environment.apiBaseUrl}/resources/${this.findFile}`;
    link.setAttribute('visibility', 'hidden');
    link.click();
  }

  private isEmpty(v: string): boolean {
    return v === undefined || v == null || v === '';
  }

}
