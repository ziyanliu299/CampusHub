package com.codes.studentsystem.dto.response;

import com.codes.studentsystem.model.EnrollmentStatus;

import java.time.Instant;

public class EnrollmentResponse {
    private Long id;
    private Long studentId;
    private Long courseId;
    private String courseCode;
    private String courseTitle;
    private EnrollmentStatus status;
    private Instant createdAt;

    public EnrollmentResponse(Long id, Long studentId, Long courseId,
                              String courseCode, String courseTitle,
                              EnrollmentStatus status, Instant createdAt) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public Long getCourseId() { return courseId; }
    public String getCourseCode() { return courseCode; }
    public String getCourseTitle() { return courseTitle; }
    public EnrollmentStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
