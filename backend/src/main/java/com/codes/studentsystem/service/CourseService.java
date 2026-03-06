package com.codes.studentsystem.service;

import com.codes.studentsystem.dto.request.CourseCreateRequest;
import com.codes.studentsystem.dto.request.CourseUpdateRequest;
import com.codes.studentsystem.dto.response.CourseResponse;
import org.springframework.data.domain.Page;

public interface CourseService {
    CourseResponse create(CourseCreateRequest req);
    CourseResponse update(Long id, CourseUpdateRequest req);
    void delete(Long id);
    CourseResponse get(Long id);
    Page<CourseResponse> list(int page, int size, String sort, String q);

}
