import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CourseService } from '../../services/course.service';
import { Course } from '../../models/course.model';

@Component({
  selector: 'app-view-courses',
  templateUrl: './view-courses.component.html',
  styleUrls: ['./view-courses.component.css']
})
export class ViewCoursesComponent implements OnInit {
  courses: Course[] = [];
  selectedCourseToDelete: Course | null = null;
  showDeleteModal: boolean = false;
  isLoading: boolean = true;

  constructor(private courseService: CourseService, private router: Router) {}

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

  editCourse(courseId?: number): void {
    if (courseId) {
      this.router.navigate(['/edit-course', courseId]);
    }
  }

  promptDelete(course: Course): void {
    this.selectedCourseToDelete = course;
    this.showDeleteModal = true;
  }

  cancelDelete(): void {
    this.showDeleteModal = false;
    this.selectedCourseToDelete = null;
  }

  confirmDelete(): void {
    if (this.selectedCourseToDelete && this.selectedCourseToDelete.courseId) {
      this.courseService.deleteCourse(this.selectedCourseToDelete.courseId).subscribe({
        next: () => {
          this.showDeleteModal = false;
          this.selectedCourseToDelete = null;
          this.loadCourses();
        },
        error: () => {
          alert('Failed to delete course');
          this.showDeleteModal = false;
        }
      });
    }
  }
}
