import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  public apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token') || localStorage.getItem('jwtToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }

  public addToCart(course: any): Observable<any> {
    const customerId = localStorage.getItem('customerId');
    const courseId = course.courseId || course;
    if (customerId && courseId) {
      return this.http.post<any>(`${this.apiUrl}/api/cart/add/${customerId}/${courseId}`, {}, { headers: this.getHeaders() });
    }
    return this.http.post<any>(`${this.apiUrl}/api/cart`, course, { headers: this.getHeaders() });
  }

  public updateCart(cartDetails: any): Observable<any> {
    const cartId = localStorage.getItem('cartId') || cartDetails.cartId;
    return this.http.put<any>(`${this.apiUrl}/api/cart/${cartId}`, cartDetails, { headers: this.getHeaders() });
  }

  public removeCoursesFromCart(courseId: any): Observable<any> {
    const cartId = localStorage.getItem('cartId') || '1';
    return this.http.delete<any>(`${this.apiUrl}/api/cart/${cartId}/course/${courseId}`, { headers: this.getHeaders() });
  }

  public removeAllCourses(): Observable<any> {
    const cartId = localStorage.getItem('cartId') || '1';
    return this.http.delete<any>(`${this.apiUrl}/api/cart/${cartId}`, { headers: this.getHeaders() });
  }

  public getCoursesFromCart(cartId: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/cart/${cartId}`, { headers: this.getHeaders() });
  }

  public getAllCoursesFromCart(): Observable<any> {
    const customerId = localStorage.getItem('customerId');
    if (customerId) {
      return this.http.get<any>(`${this.apiUrl}/api/cart/customer/${customerId}`, { headers: this.getHeaders() });
    }
    const userId = localStorage.getItem('userId');
    return this.http.get<any>(`${this.apiUrl}/api/cart/user/${userId}`, { headers: this.getHeaders() });
  }

  public addReview(review: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/api/review`, review, { headers: this.getHeaders() });
  }

  public getAllReviews(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/review`, { headers: this.getHeaders() });
  }
}
