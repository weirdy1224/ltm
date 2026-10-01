import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.authService.isLoggedIn()) {
      const expectedRole = route.data['role'];
      if (expectedRole) {
        const userRole = this.authService.getRole();
        if (userRole && userRole.toUpperCase() === expectedRole.toUpperCase()) {
          return true;
        } else {
          // Redirect to appropriate dashboard based on role
          if (this.authService.isAdmin()) {
            this.router.navigate(['/dashboard']);
          } else {
            this.router.navigate(['/customerdashboard']);
          }
          return false;
        }
      }
      return true;
    }

    // Redirect to login page if not logged in
    this.router.navigate(['/login']);
    return false;
  }
}
