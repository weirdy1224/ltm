import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { Order } from '../../models/order.model';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {
  orders: Order[] = [];
  isLoading: boolean = true;
  isAdmin: boolean = false;

  constructor(
    private orderService: OrderService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.loadOrders();
  }

  loadOrders(): void {
    this.isLoading = true;
    if (this.isAdmin) {
      this.orderService.viewAllOrders().subscribe({
        next: (data) => {
          this.orders = data || [];
          this.isLoading = false;
        },
        error: () => {
          this.orders = [];
          this.isLoading = false;
        }
      });
    } else {
      this.orderService.viewOrderByUserId().subscribe({
        next: (data) => {
          this.orders = data || [];
          this.isLoading = false;
        },
        error: () => {
          this.orders = [];
          this.isLoading = false;
        }
      });
    }
  }

  navigateToAddReview(): void {
    this.router.navigate(['/add-review']);
  }
}
