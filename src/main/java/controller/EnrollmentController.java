package controller;

import dto.EnrollmentRequest;
import dto.StudentCourseDTO;
import org.springframework.web.bind.annotation.*;
import service.EnrollmentService;

import java.util.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }


    @GetMapping
    public List<StudentCourseDTO> getAllEnrollments() throws Exception {
        return enrollmentService.viewAllEnrollments();
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable int id) throws Exception {
        enrollmentService.cancelEnrollment(id);
    }

    @PostMapping
    public void createEnrollment(@RequestBody EnrollmentRequest enrollmentRequest) throws Exception {
        enrollmentService.enrollCourse(enrollmentRequest.getStudentId(), enrollmentRequest.getCourseId());
    }
}