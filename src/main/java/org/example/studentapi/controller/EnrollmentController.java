package org.example.studentapi.controller;

import org.example.studentapi.dto.request.EnrollmentRequest;
import org.example.studentapi.dto.response.StudentCourseDTO;
import org.example.studentapi.model.StudentCourse;
import org.springframework.web.bind.annotation.*;
import org.example.studentapi.service.EnrollmentService;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<StudentCourseDTO> getAll() throws Exception {
        log.info("GET/api/enrollments");
        return enrollmentService.viewAllEnrollments();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        log.info("DELETE/api/enrollments/{}", id);
        enrollmentService.cancelEnrollment(id);
    }

    @PostMapping
    public StudentCourseDTO create(@RequestBody EnrollmentRequest enrollmentRequest) throws Exception {
        log.info("POST/api/enrollments");
        return enrollmentService.enrollCourse(enrollmentRequest);
    }
}