package com.codes.studentsystem.service;

import com.codes.studentsystem.dto.response.EnrollmentResponse;
import com.codes.studentsystem.exception.ConflictException;
import com.codes.studentsystem.exception.NotFoundException;
import com.codes.studentsystem.model.*;
import com.codes.studentsystem.repository.AppUserRepository;
import com.codes.studentsystem.repository.CourseRepository;
import com.codes.studentsystem.repository.EnrollmentRepository;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.codes.studentsystem.exception.CapacityFullException;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final AppUserRepository userRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 CourseRepository courseRepository,
                                 AppUserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public EnrollmentResponse enrollMyself(Long courseId) {
        Integer userId = currentUserId();

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("course not found: " + courseId));

        Enrollment existing = enrollmentRepository.findByUserIdAndCourseId(userId, courseId).orElse(null);
        if (existing != null && existing.getStatus() == EnrollmentStatus.ENROLLED) {
            throw new ConflictException("already enrolled");
        }

        int updated = courseRepository.tryIncrementEnrollment(courseId);
        if (updated == 0) {
            throw new CapacityFullException("course is full");
        }

        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user not found: " + userId));


        try {
            Enrollment saved;
            if (existing != null) {
                existing.setStatus(EnrollmentStatus.ENROLLED);
                saved = enrollmentRepository.save(existing);
            } else {
                Enrollment e = new Enrollment(user, course);
                saved = enrollmentRepository.save(e);
            }

            return toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("already enrolled");
        }
    }

    @Override
    @Transactional
    public EnrollmentResponse dropMyself(Long courseId) {
        Integer userId = currentUserId();

        Enrollment e = enrollmentRepository.findByUserIdAndCourseId(userId, courseId)
                .orElseThrow(() -> new NotFoundException("enrollment not found"));

        if (e.getStatus() == EnrollmentStatus.DROPPED) {
            throw new ConflictException("already dropped");
        }

        e.setStatus(EnrollmentStatus.DROPPED);
        Enrollment saved = enrollmentRepository.save(e);

        int updated = courseRepository.decrementEnrollment(courseId);
        if (updated == 0) {
            throw new ConflictException("course enrollment count is already zero");
        }

        return toResponse(saved);
    }


    @Override
    public Page<EnrollmentResponse> listMyEnrollments(int page, int size, String sort) {
        Integer userId = currentUserId();
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));

        return enrollmentRepository
                .findByUserIdAndStatus(userId, EnrollmentStatus.ENROLLED, pageable)
                .map(this::toResponse);
    }

    @Override
    public Page<EnrollmentResponse> listEnrollmentsByCourse(Long courseId, int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        return enrollmentRepository
                .findByCourseIdAndStatus(courseId, EnrollmentStatus.ENROLLED, pageable)
                .map(this::toResponse);
    }

    private EnrollmentResponse toResponse(Enrollment e) {
        return new EnrollmentResponse(
                e.getId(),
                e.getUser().getId(),     // ✅ userId (Integer)
                e.getCourse().getId(),   // courseId (Long)
                e.getCourse().getCode(),
                e.getCourse().getTitle(),
                e.getStatus(),
                e.getCreatedAt()
        );
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Direction.DESC, "id");
        String[] parts = sort.split(",");
        String field = parts[0];
        Sort.Direction dir = (parts.length > 1 && "asc".equalsIgnoreCase(parts[1]))
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return Sort.by(dir, field);
    }

    private Integer currentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) throw new NotFoundException("No authentication found");

        Object details = auth.getDetails();
        if (details instanceof Integer id) return id;
        if (details instanceof Number n) return n.intValue();
        if (details instanceof String s) return Integer.parseInt(s);

        throw new NotFoundException("userId not found in auth details (check JwtAuthFilter)");
    }
}
