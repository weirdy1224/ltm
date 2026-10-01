import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-edit-course',
  templateUrl: './edit-course.component.html',
  styleUrls: ['./edit-course.component.css']
})
export class EditCourseComponent implements OnInit {
  courseId: string | number = '';
  course: Course = {
    courseType: '',
    courseImageUrl: '',
    courseDetails: '',
    coursePrice: 0
  };

  isSubmitting: boolean = false;
  errorMessage: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private courseService: CourseService
  ) {}

  ngOnInit(): void {
    this.courseId = this.route.snapshot.params['courseId'];
    if (this.courseId) {
      this.loadCourse();
    }
  }

  loadCourse(): void {
    this.courseService.getCourseById(this.courseId).subscribe({
      next: (data) => {
        if (data) {
          this.course = data;
        }
      },
      error: () => {
        this.errorMessage = 'Failed to load course details.';
      }
    });
  }

  updateCourse(): void {
    if (!this.courseId) return;

    this.isSubmitting = true;
    this.errorMessage = '';

    this.courseService.updateCourse(this.courseId, this.course).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigate(['/view-courses']);
      },
      error: (err) => {
        this.isSubmitting = false;
        this.errorMessage = 'Failed to update course.';
      }
    });
  }

  cancel(): void {
    this.router.navigate(['/view-courses']);
  }
}
