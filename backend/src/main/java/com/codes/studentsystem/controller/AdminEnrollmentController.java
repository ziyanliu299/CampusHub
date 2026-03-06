package com.codes.studentsystem.controller;

import com.codes.studentsystem.dto.response.EnrollmentResponse;
import com.codes.studentsystem.service.EnrollmentService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/enrollments")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin
public class AdminEnrollmentController {
    private final EnrollmentService enrollmentService;


    public AdminEnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/by-course/{courseId}")
    public Page<EnrollmentResponse> listByCourse(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        return enrollmentService.listEnrollmentsByCourse(courseId, page, size, sort);
    }
}
