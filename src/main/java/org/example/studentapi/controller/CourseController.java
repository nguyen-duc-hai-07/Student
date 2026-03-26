package org.example.studentapi.controller;

import org.example.studentapi.dto.CourseResponse;
import org.example.studentapi.model.Course;
import org.springframework.web.bind.annotation.*;
import org.example.studentapi.service.CourseService;

import java.util.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourse() throws Exception {
        return courseService.findAllCourse();
    }

    @GetMapping("/{id}")
    public CourseResponse findStudentByCourseId(@PathVariable int id) throws Exception {
        return courseService.viewCourseToStudents(id);
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course) throws Exception {
        courseService.addCourse(course);
        return course;
    }
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable int id) throws Exception {
        courseService.deleteCourse(id);
    }
}