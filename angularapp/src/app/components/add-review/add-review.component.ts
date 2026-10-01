import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ReviewService } from '../../services/review.service';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-add-review',
  templateUrl: './add-review.component.html',
  styleUrls: ['./add-review.component.css']
})
export class AddReviewComponent implements OnInit {
  review = {
    customerId: localStorage.getItem('customerId') || '1',
    subject: '',
    body: '',
    rating: 5
  };

  courses: Course[] = [];
  stars: number[] = [1, 2, 3, 4, 5];
  successMessage: string = '';
  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(
    private reviewService: ReviewService,
    private courseService: CourseService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCourses();
  }

  loadCourses(): void {
    this.courseService.viewAllCourses().subscribe({
      next: (data) => this.courses = data || [],
      error: () => this.courses = []
    });
  }

  setRating(rating: number): void {
    this.review.rating = rating;
  }

  isFormValid(): boolean {
    return !!(
      this.review.customerId &&
      this.review.subject && this.review.subject.trim() &&
      this.review.body && this.review.body.trim() &&
      this.review.rating && this.review.rating > 0
    );
  }

  submitReview(): void {
    if (!this.isFormValid()) return;

    this.isSubmitting = true;
    this.errorMessage = '';

    const payload = {
      subject: this.review.subject,
      body: this.review.body,
      rating: this.review.rating,
      customer: { customerId: Number(this.review.customerId) }
    };

    this.reviewService.addReview(payload).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.successMessage = 'review added successfully!';
        setTimeout(() => {
          this.router.navigate(['/view-review']);
        }, 1200);
      },
      error: () => {
        this.isSubmitting = false;
        this.errorMessage = 'Failed to submit review.';
      }
    });
  }
}
