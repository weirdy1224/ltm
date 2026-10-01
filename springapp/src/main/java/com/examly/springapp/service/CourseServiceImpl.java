package com.examly.springapp.service;

import com.examly.springapp.model.Course;
import com.examly.springapp.repository.CourseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepo courseRepo;

    @Override
    public Course addCourse(Course course) {
        logger.info("Adding new course: {}", course.getCourseType());
        Course saved = courseRepo.save(course);
        logger.info("Course created successfully with ID: {}", saved.getCourseId());
        return saved;
    }

    @Override
    public List<Course> viewAllCourses() {
        logger.debug("Fetching all courses");
        return courseRepo.findAll();
    }

    @Override
    public Course updateCourse(Long courseId, Course updatedCourse) {
        logger.info("Updating course with ID: {}", courseId);
        return courseRepo.findById(courseId).map(existing -> {
            if (updatedCourse.getCourseType() != null) {
                existing.setCourseType(updatedCourse.getCourseType());
            }
            if (updatedCourse.getCourseImageUrl() != null) {
                existing.setCourseImageUrl(updatedCourse.getCourseImageUrl());
            }
            if (updatedCourse.getCourseDetails() != null) {
                existing.setCourseDetails(updatedCourse.getCourseDetails());
            }
            if (updatedCourse.getCoursePrice() != null) {
                existing.setCoursePrice(updatedCourse.getCoursePrice());
            }
            Course saved = courseRepo.save(existing);
            logger.info("Successfully updated course ID: {}", courseId);
            return saved;
        }).orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));
    }

    @Override
    public boolean deleteCourse(Long courseId) {
        logger.info("Deleting course with ID: {}", courseId);
        if (courseRepo.existsById(courseId)) {
            courseRepo.deleteById(courseId);
            logger.info("Deleted course ID: {}", courseId);
            return true;
        }
        logger.warn("Course with ID {} does not exist to delete", courseId);
        return false;
    }

    @Override
    public Optional<Course> getCourseById(Long courseId) {
        logger.debug("Retrieving course by ID: {}", courseId);
        return courseRepo.findById(courseId);
    }
}
