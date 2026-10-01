import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent {
  course: Course = {
    courseType: '',
    courseImageUrl: '',
    courseDetails: '',
    coursePrice: 0
  };

  successMessage: string = '';
  errorMessage: string = '';
  isSubmitting: boolean = false;

  constructor(private courseService: CourseService, private router: Router) {}

  increasePrice(): void {
    if (this.course.coursePrice === undefined || this.course.coursePrice === null) {
      this.course.coursePrice = 0;
    }
    this.course.coursePrice += 100;
  }

  decreasePrice(): void {
    if (this.course.coursePrice && this.course.coursePrice >= 100) {
      this.course.coursePrice -= 100;
    } else {
      this.course.coursePrice = 0;
    }
  }

  isFormValid(): boolean {
    return !!(
      this.course.courseType && this.course.courseType.trim() &&
      this.course.courseImageUrl && this.course.courseImageUrl.trim() &&
      this.course.courseDetails && this.course.courseDetails.trim() &&
      this.course.coursePrice !== undefined && this.course.coursePrice !== null && this.course.coursePrice > 0
    );
  }

  addCourse(): void {
    if (!this.isFormValid()) return;

    this.isSubmitting = true;
    this.errorMessage = '';

    this.courseService.addCourse(this.course).subscribe({
      next: (res) => {
        this.isSubmitting = false;
        this.successMessage = 'Course added successfully!';
        alert('Course added');
        setTimeout(() => {
          this.router.navigate(['/view-courses']);
        }, 1000);
      },
      error: (err) => {
        this.isSubmitting = false;
        this.errorMessage = 'Failed to add course. Please check inputs.';
      }
    });
  }
}
