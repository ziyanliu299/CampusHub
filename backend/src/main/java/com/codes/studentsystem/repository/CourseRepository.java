package com.codes.studentsystem.repository;
import com.codes.studentsystem.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long>{
    boolean existsByCodeIgnoreCase(String code);

    Page<Course> findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(String codeQ, String titleQ, Pageable pageable);

    @Modifying
    @Query("""
        update Course c
        set c.enrolledCount = c.enrolledCount + 1
        where c.id = :courseId
          and c.enrolledCount < c.capacity
    """)
    int tryIncrementEnrollment(@Param("courseId") Long courseId);



    @Modifying
    @Query("""
        update Course c
        set c.enrolledCount = c.enrolledCount - 1
        where c.id = :courseId
          and c.enrolledCount > 0
    """)
    int decrementEnrollment(@Param("courseId") Long courseId);



}
