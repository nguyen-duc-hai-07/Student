package org.example.studentapi.controller;

import org.example.studentapi.dto.request.EnrollmentRequest;
import org.example.studentapi.dto.response.StudentCourseDTO;
import org.example.studentapi.model.StudentCourse;
import org.springframework.web.bind.annotation.*;
import org.example.studentapi.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentController.class);
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public List<StudentCourseDTO> getAll() {
        try {

            logger.info("API GET/api/enrollments");

            return enrollmentService.viewAllEnrollments();
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            logger.info("API DELETE /api/enrollments/{} được gọi", id);
            enrollmentService.cancelEnrollment(id);
            logger.info("Xóa enrollment thành công id={}", id);
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping
    public StudentCourseDTO create(@RequestBody EnrollmentRequest enrollmentRequest) {
        try {
            logger.info("API POST /api/enrollments được gọi");
            return enrollmentService.enrollCourse(enrollmentRequest);
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}