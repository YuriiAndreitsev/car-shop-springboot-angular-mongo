import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';
import { CarServiceService } from '../car-service.service';
import { Car } from '../cars/car';

@Component({
  selector: 'app-car-search',
  templateUrl: './car-search.component.html',
  styleUrls: ['./car-search.component.css']
})
export class CarSearchComponent implements OnInit {
  cars$!: Observable<Car[]>;
  private searchTerms = new Subject<string>();
  constructor(private carService: CarServiceService) {}

  ngOnInit(): void {
    this.cars$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.carService.searchCars(term)),
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }





}
