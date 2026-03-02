package com.codes.studentsystem.service;


import com.codes.studentsystem.dto.request.CourseCreateRequest;
import com.codes.studentsystem.dto.request.CourseUpdateRequest;
import com.codes.studentsystem.dto.response.CourseResponse;
import com.codes.studentsystem.exception.ConflictException;
import com.codes.studentsystem.exception.NotFoundException;
import com.codes.studentsystem.model.Course;
import com.codes.studentsystem.model.EnrollmentStatus;
import com.codes.studentsystem.repository.CourseRepository;
import com.codes.studentsystem.repository.EnrollmentRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public CourseResponse create(CourseCreateRequest req) {
        if (courseRepository.existsByCodeIgnoreCase(req.getCode())) {
            throw new ConflictException("course code already exists: " + req.getCode());
        }
        Course c = new Course();
        c.setCode(req.getCode().trim());
        c.setTitle(req.getTitle().trim());
        c.setDescription(req.getDescription());
        c.setCapacity(req.getCapacity());

        Course saved = courseRepository.save(c);
        return toResponse(saved);
    }

    @Override
    public CourseResponse update(Long id, CourseUpdateRequest req) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("course not found: " + id));

        c.setTitle(req.getTitle().trim());
        c.setDescription(req.getDescription());
        c.setCapacity(req.getCapacity());

        Course saved = courseRepository.save(c);
        return toResponse(saved);
    }

    @Override
    public void delete(Long id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("course not found: " + id));

        long active = enrollmentRepository.countByCourseIdAndStatus(id, EnrollmentStatus.ENROLLED);
        if (active > 0) {
            throw new ConflictException("cannot delete course with active enrollments");
        }
        courseRepository.delete(c);
    }

    @Override
    public CourseResponse get(Long id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("course not found: " + id));
        return toResponse(c);
    }

    @Override
    public Page<CourseResponse> list(int page, int size, String sort, String q) {
        Sort s = parseSort(sort);
        Pageable pageable = PageRequest.of(page, size, s);

        Page<Course> p;
        if (q != null && !q.isBlank()) {
            p = courseRepository.findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(q, q, pageable);
        } else {
            p = courseRepository.findAll(pageable);
        }
        return p.map(this::toResponse);
    }

    private CourseResponse toResponse(Course c) {
        long enrolledCount = enrollmentRepository.countByCourseIdAndStatus(c.getId(), EnrollmentStatus.ENROLLED);
        return new CourseResponse(c.getId(), c.getCode(), c.getTitle(), c.getDescription(), c.getCapacity(), enrolledCount);
    }

    private Sort parseSort(String sort) {
        // sort format example: "id,desc" or "code,asc"
        if (sort == null || sort.isBlank()) return Sort.by(Sort.Direction.DESC, "id");
        String[] parts = sort.split(",");
        String field = parts[0];
        Sort.Direction dir = (parts.length > 1 && "asc".equalsIgnoreCase(parts[1])) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(dir, field);
    }
}
