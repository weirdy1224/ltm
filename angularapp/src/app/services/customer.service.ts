import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer } from '../models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  public apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token') || localStorage.getItem('jwtToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }

  public registerCustomer(customer: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/api/customer`, customer, { headers: this.getHeaders() });
  }

  public viewCustomerById(customerId: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/customer/${customerId}`, { headers: this.getHeaders() });
  }

  public viewCustomerByUserId(): Observable<any> {
    const userId = localStorage.getItem('userId');
    return this.http.get<any>(`${this.apiUrl}/api/customer/user/${userId}`, { headers: this.getHeaders() });
  }
}
