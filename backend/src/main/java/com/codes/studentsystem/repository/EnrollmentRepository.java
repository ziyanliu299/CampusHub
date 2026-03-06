package com.codes.studentsystem.repository;

import com.codes.studentsystem.model.Enrollment;
import com.codes.studentsystem.model.EnrollmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByUserIdAndCourseId(Integer userId, Long courseId);

    long countByCourseIdAndStatus(Long courseId, EnrollmentStatus status);

    Page<Enrollment> findByUserIdAndStatus(Integer userId, EnrollmentStatus status, Pageable pageable);

    Page<Enrollment> findByCourseIdAndStatus(Long courseId, EnrollmentStatus status, Pageable pageable);
}
