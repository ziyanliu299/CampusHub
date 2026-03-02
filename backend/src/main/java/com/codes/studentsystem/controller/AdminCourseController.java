package com.codes.studentsystem.controller;
import com.codes.studentsystem.dto.request.CourseCreateRequest;
import com.codes.studentsystem.dto.request.CourseUpdateRequest;
import com.codes.studentsystem.dto.response.CourseResponse;
import com.codes.studentsystem.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/courses")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin
public class AdminCourseController {


    private final CourseService courseService;

    public AdminCourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @PostMapping
    public CourseResponse create(@Valid @RequestBody CourseCreateRequest req) {
        return courseService.create(req);
    }



@PutMapping("/{id}")
public CourseResponse update(@PathVariable Long id, @Valid @RequestBody CourseUpdateRequest req) {
    return courseService.update(id, req);
}

@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
    courseService.delete(id);
}


@GetMapping("/{id}")
public CourseResponse get(@PathVariable Long id) {
    return courseService.get(id);
}
@GetMapping
public Page<CourseResponse> list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(defaultValue = "id,desc") String sort,
        @RequestParam(required = false) String q
) {
    return courseService.list(page, size, sort, q);
}
}
