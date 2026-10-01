import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  public apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token') || localStorage.getItem('jwtToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }

  public addOrder(orderData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/api/order`, orderData, { headers: this.getHeaders() });
  }

  public cancelOrder(orderId: any): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/api/order/${orderId}`, { headers: this.getHeaders() });
  }

  public viewAllOrders(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/order`, { headers: this.getHeaders() });
  }

  public viewOrderByCustomerId(): Observable<any> {
    const customerId = localStorage.getItem('customerId');
    return this.http.get<any>(`${this.apiUrl}/api/order/customer/${customerId}`, { headers: this.getHeaders() });
  }

  public viewOrderByUserId(): Observable<any> {
    const customerId = localStorage.getItem('customerId');
    if (customerId) {
      return this.http.get<any>(`${this.apiUrl}/api/order/customer/${customerId}`, { headers: this.getHeaders() });
    }
    const userId = localStorage.getItem('userId');
    return this.http.get<any>(`${this.apiUrl}/api/order/user/${userId}`, { headers: this.getHeaders() });
  }

  public viewOrderById(orderId: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/order/${orderId}`, { headers: this.getHeaders() });
  }

  public deleteOrder(orderId: any): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/api/order/${orderId}`, { headers: this.getHeaders() });
  }
}
