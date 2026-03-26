package org.example.studentapi.controller;

import org.example.studentapi.dto.StudentResponse;
import org.example.studentapi.model.Student;
import org.springframework.web.bind.annotation.*;
import org.example.studentapi.service.StudentService;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudent() throws Exception {
        return studentService.findAllStudent();
    }

    @GetMapping("/{id}")
    public StudentResponse findCourseByStudentId(@PathVariable int id) throws Exception {
        return studentService.viewStudentToCourses(id);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) throws Exception {
        studentService.addStudent(student);
        return student;
    }
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable int id) throws Exception {
        studentService.deleteStudent(id);
    }
}