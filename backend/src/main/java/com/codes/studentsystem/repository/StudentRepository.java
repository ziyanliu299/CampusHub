package com.codes.studentsystem.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codes.studentsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("""
        select s from Student s
        where lower(s.name) like lower(concat('%', :q, '%'))
           or lower(s.address) like lower(concat('%', :q, '%'))
    """)

    Page<Student> search(@Param("q") String q, Pageable pageable);

}
