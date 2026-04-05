package org.example.studentapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.studentapi.dto.request.CourseRequest;
import org.example.studentapi.dto.response.CourseResponse;
import org.example.studentapi.model.Course;
import org.springframework.web.bind.annotation.*;
import org.example.studentapi.service.CourseService;
import java.util.*;



@Slf4j
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponse> getAllCourse() throws Exception {
        log.info("GET/api/courses");
        return courseService.findAllCourse();
    }

    @GetMapping("/{id}")
    public CourseResponse findStudentByCourseId(@PathVariable int id) throws Exception {
        log.info("GET/api/courses/{}", id);
        return courseService.viewCourseToStudents(id);
    }

    @PostMapping
    public CourseResponse add(@RequestBody CourseRequest quest) throws Exception {
        log.info("POST/api/courses");
        return courseService.addCourse(quest);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        log.info("DELETE/api/courses/{}", id);
        courseService.deleteCourse(id);
    }
}