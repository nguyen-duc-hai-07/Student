package org.example.studentapi.controller;

import org.example.studentapi.dto.request.CourseRequest;
import org.example.studentapi.dto.response.CourseResponse;
import org.example.studentapi.model.Course;
import org.springframework.web.bind.annotation.*;
import org.example.studentapi.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourse() throws Exception {
        logger.info("API GET/api/courses được gọi");

        return courseService.findAllCourse();
    }

    @GetMapping("/{id}")
    public CourseResponse findStudentByCourseId(@PathVariable int id) throws Exception {

        logger.info("API GET/api/courses/{} được gọi", id);

        return courseService.viewCourseToStudents(id);
    }

    @PostMapping
    public Course add(@RequestBody CourseRequest quest) throws Exception {
        logger.info("API POST/api/courses được gọi");
        return courseService.addCourse(quest);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        logger.info("API DELETE/api/courses/{}", id);
        courseService.deleteCourse(id);
        logger.info("xóa thành công course id = {}", id);
    }
}