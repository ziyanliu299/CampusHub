package com.codes.studentsystem.controller;
import com.codes.studentsystem.dto.response.CourseResponse;
import com.codes.studentsystem.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/courses")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@CrossOrigin
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
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


    @GetMapping("/{id}")
    public CourseResponse get(@PathVariable Long id) {
        return courseService.get(id);
    }

}
