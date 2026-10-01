package com.examly.springapp.service;

import com.examly.springapp.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course addCourse(Course course);
    List<Course> viewAllCourses();
    Course updateCourse(Long courseId, Course updatedCourse);
    boolean deleteCourse(Long courseId);
    Optional<Course> getCourseById(Long courseId);
}
