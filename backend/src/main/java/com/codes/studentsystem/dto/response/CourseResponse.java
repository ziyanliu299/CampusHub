package com.codes.studentsystem.dto.response;

public class CourseResponse {
    private Long id;
    private String code;
    private String title;
    private String description;
    private Integer capacity;
    private Long enrolledCount;

    public CourseResponse(Long id, String code, String title, String description, Integer capacity, Long enrolledCount) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledCount = enrolledCount;
    }

        public Long getId() { return id; }
        public String getCode() { return code; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public Integer getCapacity() { return capacity; }
        public Long getEnrolledCount() { return enrolledCount; }
    }

