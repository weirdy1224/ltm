package com.examly.springapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String courseType;

    @Column(length = 2000)
    private String courseImageUrl;

    @Column(length = 2000)
    private String courseDetails;

    private Double coursePrice;

    public Course() {
    }

    public Course(Long courseId, String courseType, String courseImageUrl, String courseDetails, Double coursePrice) {
        this.courseId = courseId;
        this.courseType = courseType;
        this.courseImageUrl = courseImageUrl;
        this.courseDetails = courseDetails;
        this.coursePrice = coursePrice;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseImageUrl() {
        return courseImageUrl;
    }

    public void setCourseImageUrl(String courseImageUrl) {
        this.courseImageUrl = courseImageUrl;
    }

    public String getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(String courseDetails) {
        this.courseDetails = courseDetails;
    }

    public Double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Double coursePrice) {
        this.coursePrice = coursePrice;
    }
}
