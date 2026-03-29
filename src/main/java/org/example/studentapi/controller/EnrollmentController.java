package org.example.studentapi.controller;

import org.example.studentapi.dto.request.EnrollmentRequest;
import org.example.studentapi.dto.response.StudentCourseDTO;
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
    public List<StudentCourseDTO> getAll() throws Exception {
        logger.info("API GET/api/enrollments");
        List<StudentCourseDTO> dto = enrollmentService.viewAllEnrollments();
        logger.info("Trả về {} enrollments", dto.size());
        return dto;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        logger.info("API DELETE /api/enrollments/{} được gọi", id);
        enrollmentService.cancelEnrollment(id);
        logger.info("Xóa enrollment thành công id={}", id);
    }

    @PostMapping
    public void create(@RequestBody EnrollmentRequest enrollmentRequest) throws Exception {
        logger.info("API POST /api/enrollments được gọi");
        logger.info("đăng kí studentId = {}, courseId = {}" ,
                enrollmentRequest.getStudentId(),
                enrollmentRequest.getCourseId()
        );
        enrollmentService.enrollCourse(enrollmentRequest.getStudentId(), enrollmentRequest.getCourseId());
        logger.info("Enroll thành công");
    }
}