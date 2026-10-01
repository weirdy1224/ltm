import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  user = {
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
    mobileNumber: '',
    role: ''
  };

  errorMessage: string = '';
  successMessage: string = '';
  isSubmitting: boolean = false;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  isPasswordMismatch(): boolean {
    return !!(this.user.password && this.user.confirmPassword && this.user.password !== this.user.confirmPassword);
  }

  isFormValid(): boolean {
    return !!(
      this.user.username.trim() &&
      this.user.email.trim() &&
      this.user.password.trim() &&
      this.user.confirmPassword.trim() &&
      this.user.mobileNumber.trim() &&
      this.user.role.trim() &&
      !this.isPasswordMismatch()
    );
  }

  register(): void {
    if (!this.isFormValid()) return;

    this.isSubmitting = true;
    this.errorMessage = '';

    const payload = {
      username: this.user.username,
      email: this.user.email,
      password: this.user.password,
      mobileNumber: this.user.mobileNumber,
      role: this.user.role
    };

    this.authService.register(payload).subscribe({
      next: (res) => {
        this.isSubmitting = false;
        this.successMessage = 'Registration successful! Redirecting to login...';
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 1500);
      },
      error: (err) => {
        this.isSubmitting = false;
        this.errorMessage = err.error?.message || 'Registration failed. Please check inputs and try again.';
      }
    });
  }
}
