import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { User } from '../models/user.model';
import { Login } from '../models/login.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public apiUrl: string = 'http://localhost:8080';

  private roleSubject = new BehaviorSubject<string | null>(localStorage.getItem('userRole') || localStorage.getItem('role'));
  public role$ = this.roleSubject.asObservable();

  private userIdSubject = new BehaviorSubject<string | number | null>(localStorage.getItem('userId'));
  public userId$ = this.userIdSubject.asObservable();

  private customerIdSubject = new BehaviorSubject<string | number | null>(localStorage.getItem('customerId'));
  public customerId$ = this.customerIdSubject.asObservable();

  constructor(private http: HttpClient) {}

  public getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token') || localStorage.getItem('jwtToken');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token ? `Bearer ${token}` : ''
    });
  }

  public register(user: User): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/api/register`, user);
  }

  public login(login: Login): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/api/login`, login).pipe(
      tap((res: any) => {
        if (res && (res.token || res.jwtToken)) {
          const token = res.token || res.jwtToken;
          const role = res.role || res.userRole;
          const userId = res.userId;
          const customerId = res.customerId;

          localStorage.setItem('token', token);
          localStorage.setItem('jwtToken', token);

          if (role) {
            localStorage.setItem('userRole', role);
            localStorage.setItem('role', role);
            this.roleSubject.next(role);
          }

          if (userId) {
            localStorage.setItem('userId', String(userId));
            this.userIdSubject.next(userId);
          }

          if (customerId) {
            localStorage.setItem('customerId', String(customerId));
            this.customerIdSubject.next(customerId);
          }
        }
      })
    );
  }

  public logout(): void {
    localStorage.clear();
    this.roleSubject.next(null);
    this.userIdSubject.next(null);
    this.customerIdSubject.next(null);
  }

  public isLoggedIn(): boolean {
    return !!(localStorage.getItem('token') || localStorage.getItem('jwtToken'));
  }

  public getRole(): string | null {
    return localStorage.getItem('userRole') || localStorage.getItem('role');
  }

  public getUserId(): string | number | null {
    return localStorage.getItem('userId');
  }

  public getCustomerId(): string | number | null {
    return localStorage.getItem('customerId');
  }

  public isAdmin(): boolean {
    const role = this.getRole();
    return role ? role.toUpperCase() === 'ADMIN' : false;
  }

  public isCustomer(): boolean {
    const role = this.getRole();
    return role ? (role.toUpperCase() === 'CUSTOMER' || role.toUpperCase() === 'USER') : false;
  }
}
