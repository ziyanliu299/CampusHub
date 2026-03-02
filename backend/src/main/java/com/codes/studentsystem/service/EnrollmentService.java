package com.codes.studentsystem.service;

import com.codes.studentsystem.dto.response.EnrollmentResponse;
import org.springframework.data.domain.Page;

public interface EnrollmentService {

    EnrollmentResponse enrollMyself(Long courseId);
    EnrollmentResponse dropMyself(Long courseId);
    Page<EnrollmentResponse> listMyEnrollments(int page, int size, String sort);

    // optional admin
    Page<EnrollmentResponse> listEnrollmentsByCourse(Long courseId, int page, int size, String sort);
}
