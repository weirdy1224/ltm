import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginData = {
    email: '',
    password: ''
  };

  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    // If already logged in, redirect
    if (this.authService.isLoggedIn()) {
      if (this.authService.isAdmin()) {
        this.router.navigate(['/dashboard']);
      } else {
        this.router.navigate(['/customerdashboard']);
      }
    }
  }

  isFormValid(): boolean {
    return !!(this.loginData.email.trim() && this.loginData.password.trim());
  }

  login(): void {
    if (!this.isFormValid()) return;

    this.isSubmitting = true;
    this.errorMessage = '';

    this.authService.login(this.loginData).subscribe({
      next: (res) => {
        this.isSubmitting = false;
        const role = this.authService.getRole();
        if (role && role.toUpperCase() === 'ADMIN') {
          this.router.navigate(['/dashboard']);
        } else {
          this.router.navigate(['/customerdashboard']);
        }
      },
      error: (err) => {
        this.isSubmitting = false;
        this.errorMessage = err.error?.message || 'Invalid credentials. Please try again.';
      }
    });
  }
}
