import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Course } from '../models/course.model';

@Injectable({
  providedIn: 'root'
})
export class CourseService {
  public apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token') || localStorage.getItem('jwtToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }

  public addCourse(courseData: Course): Observable<Course> {
    return this.http.post<Course>(`${this.apiUrl}/api/course`, courseData, { headers: this.getHeaders() });
  }

  public viewAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.apiUrl}/api/course`, { headers: this.getHeaders() });
  }

  public updateCourse(courseId: string | number, updatedCourse: Course): Observable<Course> {
    return this.http.put<Course>(`${this.apiUrl}/api/course/${courseId}`, updatedCourse, { headers: this.getHeaders() });
  }

  public deleteCourse(courseId: string | number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/api/course/${courseId}`, { headers: this.getHeaders() });
  }

  public getCourseById(courseId: string | number): Observable<Course> {
    return this.http.get<Course>(`${this.apiUrl}/api/course/${courseId}`, { headers: this.getHeaders() });
  }
}
