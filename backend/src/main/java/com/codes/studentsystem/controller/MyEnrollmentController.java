package com.codes.studentsystem.controller;

import com.codes.studentsystem.dto.request.EnrollRequest;
import com.codes.studentsystem.dto.response.EnrollmentResponse;
import com.codes.studentsystem.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me/enrollments")
@PreAuthorize("hasRole('USER')")
@CrossOrigin
public class MyEnrollmentController {

    private final EnrollmentService enrollmentService;

    public MyEnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }


    @PostMapping
    public EnrollmentResponse enroll(@Valid @RequestBody EnrollRequest req) {
        return enrollmentService.enrollMyself(req.getCourseId());
    }

    @DeleteMapping("/{courseId}")
    public EnrollmentResponse drop(@PathVariable Long courseId) {
        return enrollmentService.dropMyself(courseId);
    }

    @GetMapping
    public Page<EnrollmentResponse> listMyEnrollments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id,desc") String sort
    ) {
        return enrollmentService.listMyEnrollments(page, size, sort);
    }


}


