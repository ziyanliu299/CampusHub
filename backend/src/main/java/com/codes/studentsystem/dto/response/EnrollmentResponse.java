package com.codes.studentsystem.dto.response;

import com.codes.studentsystem.model.EnrollmentStatus;

import java.time.Instant;

public class EnrollmentResponse {
    private final Long id;
    private final Integer userId;
    private final Long courseId;
    private final String courseCode;
    private final String courseTitle;
    private final EnrollmentStatus status;
    private final Instant createdAt;

    public EnrollmentResponse(Long id, Integer userId, Long courseId,
                              String courseCode, String courseTitle,
                              EnrollmentStatus status, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Integer getUserId() { return userId; }
    public Long getCourseId() { return courseId; }
    public String getCourseCode() { return courseCode; }
    public String getCourseTitle() { return courseTitle; }
    public EnrollmentStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
}
