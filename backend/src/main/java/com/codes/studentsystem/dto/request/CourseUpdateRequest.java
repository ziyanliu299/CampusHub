package com.codes.studentsystem.dto.request;

import jakarta.validation.constraints.*;

public class CourseUpdateRequest {

    @NotBlank
    @Size(max = 120)
    private String title;

    @Size(max = 500)
    private String description;

    @NotNull
    @Min(1)
    @Max(500)
    private Integer capacity;

    // getters/setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
}
