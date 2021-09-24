import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {HomeComponent} from './home/home.component';
import {ProfileComponent} from './profile/profile.component';
import {BoardAdminComponent} from './board-admin/board-admin.component';
import {BoardModeratorComponent} from './board-moderator/board-moderator.component';
import {BoardUserComponent} from './board-user/board-user.component';

import {authInterceptorProviders} from './_helpers/auth.interceptor';
import {CarSearchComponent} from './car-search/car-search.component';
import {CarDetailComponent} from './car-detail/car-detail.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {CarsComponent} from './cars/cars.component';
import {NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {AddCarComponent} from './add-car/add-car.component';
import {RouterModule} from "@angular/router";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCheckboxModule} from "@angular/material/checkbox";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    ProfileComponent,
    BoardAdminComponent,
    BoardModeratorComponent,
    BoardUserComponent,
    CarSearchComponent,
    DashboardComponent,
    CarDetailComponent,
    CarsComponent,
    AddCarComponent,
  ],
  imports: [
    MatCheckboxModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    FormsModule,
    HttpClientModule,
    NgbPaginationModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule {
}
