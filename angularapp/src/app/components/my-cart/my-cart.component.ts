import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { Cart } from '../../models/cart.model';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-my-cart',
  templateUrl: './my-cart.component.html',
  styleUrls: ['./my-cart.component.css']
})
export class MyCartComponent implements OnInit {
  cart: Cart = { courses: [], totalAmount: 0 };
  isLoading: boolean = true;

  constructor(private cartService: CartService, private router: Router) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.isLoading = true;
    this.cartService.getAllCoursesFromCart().subscribe({
      next: (data) => {
        if (data) {
          this.cart = data;
          if (data.cartId) {
            localStorage.setItem('cartId', String(data.cartId));
          }
        } else {
          this.cart = { courses: [], totalAmount: 0 };
        }
        this.isLoading = false;
      },
      error: () => {
        this.cart = { courses: [], totalAmount: 0 };
        this.isLoading = false;
      }
    });
  }

  removeFromCart(courseId?: number): void {
    if (!courseId) return;

    this.cartService.removeCoursesFromCart(courseId).subscribe({
      next: (updatedCart) => {
        if (updatedCart) {
          this.cart = updatedCart;
        } else {
          this.loadCart();
        }
      },
      error: () => {
        alert('Failed to remove item from cart.');
      }
    });
  }

  enrollNow(): void {
    this.router.navigate(['/place-order']);
  }
}
