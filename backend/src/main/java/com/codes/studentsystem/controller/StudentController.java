package com.codes.studentsystem.controller;
import com.codes.studentsystem.dto.request.StudentCreateRequest;
import com.codes.studentsystem.dto.request.StudentUpdateRequest;
import com.codes.studentsystem.dto.response.StudentResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.codes.studentsystem.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000")

public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentResponse create(@Valid @RequestBody StudentCreateRequest request) {
        return studentService.create(request);
    }

    @GetMapping
    public Page<StudentResponse> getAll(
            @RequestParam(required = false) String q,
            @PageableDefault(size = 10) Pageable pageable) {
        return studentService.getAll(q, pageable);
    }

    @PutMapping("/{id}")
    public StudentResponse update(@PathVariable Integer id, @Valid @RequestBody StudentUpdateRequest request) {
        return studentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        studentService.delete(id);
    }
}
