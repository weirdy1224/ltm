import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {
  public apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token') || localStorage.getItem('jwtToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }

  public addReview(review: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/api/review`, review, { headers: this.getHeaders() });
  }

  public getAllReviews(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/review`, { headers: this.getHeaders() });
  }

  public getReviewById(reviewId: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/review/${reviewId}`, { headers: this.getHeaders() });
  }

  public getReviewsByUserId(userId: any): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/api/review/user/${userId}`, { headers: this.getHeaders() });
  }

  public deleteReview(reviewId: any): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/api/review/${reviewId}`, { headers: this.getHeaders() });
  }
}
