import { Component, OnInit } from '@angular/core';
import { CourseService } from '../../services/course.service';
import { CartService } from '../../services/cart.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-customer-view-courses',
  templateUrl: './customer-view-courses.component.html',
  styleUrls: ['./customer-view-courses.component.css']
})
export class CustomerViewCoursesComponent implements OnInit {
  courses: Course[] = [];
  isLoading: boolean = true;
  successMessage: string = '';
  addedCourseName: string = '';

  constructor(
    private courseService: CourseService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses(): void {
    this.isLoading = true;
    this.courseService.viewAllCourses().subscribe({
      next: (data) => {
        this.courses = data || [];
        this.isLoading = false;
      },
      error: () => {
        this.courses = [];
        this.isLoading = false;
      }
    });
  }

  addToCart(course: Course): void {
    this.cartService.addToCart(course).subscribe({
      next: () => {
        this.addedCourseName = course.courseType || 'Course';
        this.successMessage = `${this.addedCourseName} added to your cart`;
        setTimeout(() => {
          this.successMessage = '';
        }, 3000);
      },
      error: (err) => {
        alert('Failed to add course to cart.');
      }
    });
  }
}
