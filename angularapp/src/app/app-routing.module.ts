import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { HomeComponent } from './components/home/home.component';
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
import { AuthGuard } from './components/authguard/authguard.service';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'home', component: HomeComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'customerdashboard', component: CustomerdashboardComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'add-course', component: AddCourseComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'edit-course/:courseId', component: EditCourseComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'view-courses', component: ViewCoursesComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'customer-view-courses', component: CustomerViewCoursesComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'my-cart', component: MyCartComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'place-order', component: PlaceOrderComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'my-orders', component: MyOrdersComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'view-orders', component: MyOrdersComponent, canActivate: [AuthGuard], data: { role: 'ADMIN' } },
  { path: 'add-review', component: AddReviewComponent, canActivate: [AuthGuard], data: { role: 'CUSTOMER' } },
  { path: 'view-review', component: ViewReviewComponent, canActivate: [AuthGuard] },
  { path: 'error', component: ErrorComponent },
  { path: '**', redirectTo: 'login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
