package org.example.studentapi.controller;

import org.example.studentapi.dto.request.StudentRequest;
import org.example.studentapi.dto.response.StudentResponse;
import org.example.studentapi.model.Student;
import org.springframework.web.bind.annotation.*;
import org.example.studentapi.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudent() {
        try {
            logger.info("API GET /api/students được gọi");

            return studentService.findAllStudent();
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public StudentResponse findCourseByStudentId(@PathVariable int id) {
        try {
            logger.info("API GET /api/students/{} được gọi", id);

            return studentService.viewStudentToCourses(id);
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping
    public StudentResponse add(@RequestBody StudentRequest quest) {
        try {
            logger.info("API POST /api/students được gọi");

            return studentService.addStudent(quest);
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new RuntimeException(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            logger.info("API DELETE /api/students/{} được gọi", id);

            studentService.deleteStudent(id);

            logger.info("Xóa student thành công id={}", id);
        } catch (Exception e) {
            logger.error("Error: ", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}