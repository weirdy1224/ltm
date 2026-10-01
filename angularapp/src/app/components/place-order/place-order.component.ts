import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CustomerService } from '../../services/customer.service';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-place-order',
  templateUrl: './place-order.component.html',
  styleUrls: ['./place-order.component.css']
})
export class PlaceOrderComponent implements OnInit {
  customerDetails = {
    name: '',
    mobileNumber: '',
    information: '',
    email: '',
    customerId: null as number | null
  };

  coursesInCart: Course[] = [];
  totalPrice: number = 0;
  isProcessing: boolean = false;
  showPaymentModal: boolean = false;

  constructor(
    private customerService: CustomerService,
    private cartService: CartService,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadUserData();
    this.loadCartData();
  }

  loadUserData(): void {
    this.customerService.viewCustomerByUserId().subscribe({
      next: (cust) => {
        if (cust) {
          this.customerDetails.customerId = cust.customerId || null;
          this.customerDetails.name = cust.customerName || '';
          this.customerDetails.information = cust.information || '';
          if (cust.user) {
            this.customerDetails.email = cust.user.email || '';
            this.customerDetails.mobileNumber = cust.user.mobileNumber || '';
          }
        }
      }
    });
  }

  loadCartData(): void {
    this.cartService.getAllCoursesFromCart().subscribe({
      next: (cart) => {
        if (cart) {
          this.coursesInCart = cart.courses || [];
          this.totalPrice = cart.totalAmount || 0;
        }
      }
    });
  }

  makePayment(): void {
    this.isProcessing = true;
    const customerId = localStorage.getItem('customerId') || this.customerDetails.customerId;

    const orderPayload = {
      orderPrice: this.totalPrice,
      courses: this.coursesInCart,
      customer: { customerId: customerId ? Number(customerId) : null }
    };

    this.orderService.addOrder(orderPayload).subscribe({
      next: () => {
        this.isProcessing = false;
        this.showPaymentModal = true;
      },
      error: () => {
        this.isProcessing = false;
        alert('Payment failed. Please try again.');
      }
    });
  }

  confirmPaymentSuccess(): void {
    this.showPaymentModal = false;
    this.router.navigate(['/my-orders']);
  }
}
