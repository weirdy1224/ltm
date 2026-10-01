import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CustomerdashboardComponent } from './components/customerdashboard/customerdashboard.component';
import { AddCourseComponent } from './components/add-course/add-course.component';
import { EditCourseComponent } from './components/edit-course/edit-course.component';
import { ViewCoursesComponent } from './components/view-courses/view-courses.component';
import { CustomerViewCoursesComponent } from './components/customer-view-courses/customer-view-courses.component';
import { MyCartComponent } from './components/my-cart/my-cart.component';
import { PlaceOrderComponent } from './components/place-order/place-order.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';
import { AddReviewComponent } from './components/add-review/add-review.component';
import { ViewReviewComponent } from './components/view-review/view-review.component';
import { ErrorComponent } from './components/error/error.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
    RegistrationComponent,
    DashboardComponent,
    CustomerdashboardComponent,
    AddCourseComponent,
    EditCourseComponent,
    ViewCoursesComponent,
    CustomerViewCoursesComponent,
    MyCartComponent,
    PlaceOrderComponent,
    MyOrdersComponent,
    AddReviewComponent,
    ViewReviewComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
