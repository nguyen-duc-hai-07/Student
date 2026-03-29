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
    public List<Student> getAllStudent() throws Exception {
        logger.info("API GET /api/students được gọi");
        List<Student> students = studentService.findAllStudent();
        logger.info("Trả về {} students", students.size());
        return students;
    }

    @GetMapping("/{id}")
    public StudentResponse findCourseByStudentId(@PathVariable int id) throws Exception {
        logger.info("API GET /api/students/{} được gọi", id);

        StudentResponse response = studentService.viewStudentToCourses(id);

        logger.info("Trả về thông tin student id={}", id);

        return response;
    }

    @PostMapping
    public Student add(@RequestBody StudentRequest quest) throws Exception {
        logger.info("API POST /api/students được gọi");

        Student student = new Student(quest.getName(), quest.getEmail(), quest.getPhone());

        studentService.addStudent(student);

        logger.info("Thêm student thành công: {}", student.getName());

        return student;
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        logger.info("API DELETE /api/students/{} được gọi", id);

        studentService.deleteStudent(id);

        logger.info("Xóa student thành công id={}", id);
    }
}