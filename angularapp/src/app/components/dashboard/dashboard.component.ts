import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { OrderService } from '../../services/order.service';
import { ReviewService } from '../../services/review.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  totalCourses: number = 0;
  totalOrders: number = 0;
  totalReviews: number = 0;

  constructor(
    private courseService: CourseService,
    private orderService: OrderService,
    private reviewService: ReviewService
  ) {}

  ngOnInit(): void {
    this.loadStats();
  }

  loadStats(): void {
    this.courseService.viewAllCourses().subscribe({
      next: (courses) => this.totalCourses = courses ? courses.length : 0,
      error: () => this.totalCourses = 0
    });

    this.orderService.viewAllOrders().subscribe({
      next: (orders) => this.totalOrders = orders ? orders.length : 0,
      error: () => this.totalOrders = 0
    });

    this.reviewService.getAllReviews().subscribe({
      next: (reviews) => this.totalReviews = reviews ? reviews.length : 0,
      error: () => this.totalReviews = 0
    });
  }
}
