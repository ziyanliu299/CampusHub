package com.codes.studentsystem.service;

import com.codes.studentsystem.dto.request.StudentCreateRequest;
import com.codes.studentsystem.dto.request.StudentUpdateRequest;
import com.codes.studentsystem.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    StudentResponse create(StudentCreateRequest request);
    Page<StudentResponse> getAll(String q, Pageable pageable);
    StudentResponse update(Integer id, StudentUpdateRequest request);
    void delete(Integer id);
}
