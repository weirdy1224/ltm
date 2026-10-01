import { Component, OnInit } from '@angular/core';
import { ReviewService } from '../../services/review.service';
import { AuthService } from '../../services/auth.service';
import { Review } from '../../models/review.model';

@Component({
  selector: 'app-view-review',
  templateUrl: './view-review.component.html',
  styleUrls: ['./view-review.component.css']
})
export class ViewReviewComponent implements OnInit {
  reviews: Review[] = [];
  isLoading: boolean = true;
  isAdmin: boolean = false;

  constructor(
    private reviewService: ReviewService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.isAdmin = this.authService.isAdmin();
    this.loadReviews();
  }

  loadReviews(): void {
    this.isLoading = true;
    this.reviewService.getAllReviews().subscribe({
      next: (data) => {
        this.reviews = data || [];
        this.isLoading = false;
      },
      error: () => {
        this.reviews = [];
        this.isLoading = false;
      }
    });
  }

  deleteReview(reviewId?: number): void {
    if (!reviewId) return;
    if (confirm('Are you sure you want to delete this review?')) {
      this.reviewService.deleteReview(reviewId).subscribe({
        next: () => this.loadReviews(),
        error: () => alert('Failed to delete review.')
      });
    }
  }
}
