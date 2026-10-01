import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerService } from '../../services/customer.service';
import { Customer } from '../../models/customer.model';

@Component({
  selector: 'app-customerdashboard',
  templateUrl: './customerdashboard.component.html',
  styleUrls: ['./customerdashboard.component.css']
})
export class CustomerdashboardComponent implements OnInit {
  customer: Customer = {
    customerName: '',
    information: ''
  };

  isSaved: boolean = false;
  isSubmitting: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private customerService: CustomerService, private router: Router) {}

  ngOnInit(): void {
    this.loadCustomerDetails();
  }

  loadCustomerDetails(): void {
    this.customerService.viewCustomerByUserId().subscribe({
      next: (data) => {
        if (data) {
          this.customer = data;
          if (data.customerId) {
            localStorage.setItem('customerId', String(data.customerId));
          }
          if (data.customerName && data.information) {
            this.isSaved = true;
          }
        }
      },
      error: () => {
        // Customer profile might be fresh
      }
    });
  }

  isFormValid(): boolean {
    return !!(this.customer.customerName && this.customer.customerName.trim() && 
              this.customer.information && this.customer.information.trim());
  }

  submitDetails(): void {
    if (!this.isFormValid()) return;

    this.isSubmitting = true;
    const userId = localStorage.getItem('userId');

    const payload = {
      customerId: this.customer.customerId,
      customerName: this.customer.customerName,
      information: this.customer.information,
      user: { userId: userId ? Number(userId) : null }
    };

    this.customerService.registerCustomer(payload).subscribe({
      next: (res) => {
        this.isSubmitting = false;
        if (res && res.customerId) {
          localStorage.setItem('customerId', String(res.customerId));
        }
        this.successMessage = 'Profile updated successfully! Redirecting to available courses...';
        setTimeout(() => {
          this.router.navigate(['/customer-view-courses']);
        }, 1200);
      },
      error: (err) => {
        this.isSubmitting = false;
        this.errorMessage = 'Failed to save details. Please try again.';
      }
    });
  }
}
