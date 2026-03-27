package org.example.studentapi.controller;

import org.example.studentapi.dto.request.EnrollmentRequest;
import org.example.studentapi.dto.response.StudentCourseDTO;
import org.springframework.web.bind.annotation.*;
import org.example.studentapi.service.EnrollmentService;

import java.util.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<StudentCourseDTO> getAll() throws Exception {
        return enrollmentService.viewAllEnrollments();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        enrollmentService.cancelEnrollment(id);
    }

    @PostMapping
    public void create(@RequestBody EnrollmentRequest enrollmentRequest) throws Exception {
        enrollmentService.enrollCourse(enrollmentRequest.getStudentId(), enrollmentRequest.getCourseId());
    }
}