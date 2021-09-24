import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {Car} from './cars/car';
import {MessageService} from './message.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {catchError, map, take, tap} from 'rxjs/operators';
import {Task} from './payload/task';

const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

@Injectable({
  providedIn: 'root'
})
export class CarServiceService {
  constructor(private http: HttpClient, private messageService: MessageService) {
  }

  private apiServerUrl = environment.apiBaseUrl;
  private SEND_CAR_PARAMS = `${this.apiServerUrl}/api/cars/unique`;
  private GET_ALL_CARS = `${this.apiServerUrl}/api/cars/all`;
  private GET_ALL_CARS_BY_BRAND = `${this.apiServerUrl}/api/cars/all-by-brand/`;
  private GET_BRANDS = `${this.apiServerUrl}/api/cars/brands`;
  private GET_CAR_BY_ID = `${this.apiServerUrl}/api/cars/`;
  private SEARCH_CAR_BY_TERM = `${this.apiServerUrl}/api/cars/search/`;
  private ADD_CAR = `${this.apiServerUrl}/api/cars/add`;
  private DELETE_CAR_BY_ID = `${this.apiServerUrl}/api/cars/delete/`;
  private UPDATE_CAR = `${this.apiServerUrl}/api/cars/update/`;
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  getAllCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.GET_ALL_CARS).pipe(
      tap(_ => this.log('fetched cars')),
      catchError(this.handleError<Car[]>('getAllCars', []))
    );
  }

  getBrands(): Observable<string[]> {
    return this.http.get<string[]>(this.GET_BRANDS).pipe(
      tap(s => console.log(s)));
  }

  getAllCarsByBrand(brand: string): Observable<Car[]> {
    return this.http.get<Car[]>(this.GET_ALL_CARS_BY_BRAND + brand).pipe(
      tap(_ => this.log('fetched cars by brand')),
      catchError(this.handleError<Car[]>('getAllCarsByBrand', []))
    );
  }

  getCar(id: string): Observable<Car> {
    return this.http.get<Car>(this.GET_CAR_BY_ID + id).pipe(
      tap(_ => this.log(`fetched car id=${id}`)),
      catchError(this.handleError<Car>(`getCar id=${id}`))
    );
  }

  /* GET cars which model contains search term */
  searchCars(term: string): Observable<Car[]> {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<Car[]>(this.SEARCH_CAR_BY_TERM + `${term}`).pipe(
      tap(x => x.length ?
        this.log(`found cars matching "${term}"`) :
        this.log(`no cars matching "${term}"`)),
      catchError(this.handleError<Car[]>('searchCars', []))
    );
  }

  addCar(car: Car): Observable<Car> {
    return this.http.post<Car>(this.ADD_CAR, car, this.httpOptions).pipe(
      tap((newCar: Car) => this.log(`added  car w/ id=${newCar.id}`)),
      catchError(this.handleError<Car>('addCar'))
    );
  }

  deleteCar(id: string): Observable<Car> {
    return this.http.delete<Car>(this.DELETE_CAR_BY_ID + id, this.httpOptions).pipe(
      tap(_ => this.log(`deleted car id=${id}`)),
      catchError(this.handleError<Car>('deleteCar'))
    );
  }

  updateCar(car: Car): Observable<any> {
    return this.http.put(this.UPDATE_CAR, car, this.httpOptions).pipe(
      tap(_ => this.log(`updated car id=${car.id}`)),
      catchError(this.handleError<any>('updateCar'))
    );
  }


  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  private log(message: string) {
    this.messageService.add(`HeroService: ${message}`);
  }


  sendTask(task: Task): Observable<Car[]> {
    console.log(task);
    return this.http.post<Car[]>(this.SEND_CAR_PARAMS, task, {headers: headers}); //.pipe(take(1))
  }
}
