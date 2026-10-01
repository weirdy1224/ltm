package com.examly.springapp.controller;

import com.examly.springapp.model.Course;
import com.examly.springapp.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
@CrossOrigin(origins = "*")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        logger.info("REST request to add new course: {}", course.getCourseType());
        try {
            Course savedCourse = courseService.addCourse(course);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error adding course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        logger.info("REST request to get all courses");
        return ResponseEntity.ok(courseService.viewAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId) {
        logger.info("REST request to get course by ID: {}", courseId);
        Optional<Course> courseOpt = courseService.getCourseById(courseId);
        if (courseOpt.isPresent()) {
            return ResponseEntity.ok(courseOpt.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "Course not found with id: " + courseId));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<?> getCourseByCoursesPathId(@PathVariable Long courseId) {
        logger.info("REST request to get course by path /courses/{}", courseId);
        return getCourseById(courseId);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody Course updatedCourse) {
        logger.info("REST request to update course ID: {}", courseId);
        try {
            Course course = courseService.updateCourse(courseId, updatedCourse);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            logger.error("Error updating course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        logger.info("REST request to delete course ID: {}", courseId);
        boolean deleted = courseService.deleteCourse(courseId);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Course deleted successfully"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Unable to delete course"));
    }
}
