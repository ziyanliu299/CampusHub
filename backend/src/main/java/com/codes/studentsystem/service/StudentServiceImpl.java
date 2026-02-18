package com.codes.studentsystem.service;

import com.codes.studentsystem.dto.request.StudentCreateRequest;
import com.codes.studentsystem.dto.request.StudentUpdateRequest;
import com.codes.studentsystem.dto.response.StudentResponse;
import com.codes.studentsystem.exception.NotFoundException;
import com.codes.studentsystem.model.Student;
import com.codes.studentsystem.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;


import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentResponse create(StudentCreateRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setAddress(request.getAddress());

        Student saved = studentRepository.save(student);
        return toResponse(saved);
    }

    @Override
    public Page<StudentResponse> getAll(String q, Pageable pageable) {
        Page<Student> page;
        if(StringUtils.hasText(q)){
            page = studentRepository.search(q.trim(), pageable);
        }else{
            page = studentRepository.findAll(pageable);
        }
        return page.map(this::toResponse);
    }

    @Override
    public StudentResponse update(Integer id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found: " + id));

        student.setName(request.getName());
        student.setAddress(request.getAddress());

        Student saved = studentRepository.save(student);
        return toResponse(saved);
    }

    @Override
    public void delete(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new NotFoundException("Student not found: " + id);
        }
        studentRepository.deleteById(id);
    }

    private StudentResponse toResponse(Student s) {
        return new StudentResponse(s.getId(), s.getName(), s.getAddress());
    }
}
