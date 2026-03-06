package com.codes.studentsystem.repository;
import com.codes.studentsystem.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>{
    boolean existsByCodeIgnoreCase(String code);

    Page<Course> findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(String codeQ, String titleQ, Pageable pageable);

}
