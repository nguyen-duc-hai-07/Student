package org.example.studentapi.controller;

import org.example.studentapi.dto.request.StudentRequest;
import org.example.studentapi.dto.response.StudentResponse;
import org.example.studentapi.model.Student;
import org.springframework.web.bind.annotation.*;
import org.example.studentapi.service.StudentService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentResponse> getAllStudent() throws Exception{
        log.info("GET/api/students");
        return studentService.findAllStudent();
    }

    @GetMapping("/{id}")
    public StudentResponse findCourseByStudentId(@PathVariable int id) throws Exception {
        log.info("GET/api/students/{}", id);
        return studentService.viewStudentToCourses(id);
    }

    @PostMapping
    public StudentResponse add(@RequestBody StudentRequest quest) throws Exception {
        log.info("POST/api/students");
        return studentService.addStudent(quest);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) throws Exception {
        log.info("DELETE/api/students/{}", id);
        studentService.deleteStudent(id);
    }
}