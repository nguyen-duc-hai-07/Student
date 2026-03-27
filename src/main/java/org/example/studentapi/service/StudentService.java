package org.example.studentapi.service;

import org.example.studentapi.dto.response.StudentResponse;
import org.example.studentapi.model.Student;

import java.util.List;

public interface StudentService {

    void addStudent(Student student) throws Exception;

    void deleteStudent(int id) throws Exception;

    List<Student> findAllStudent() throws Exception;

    StudentResponse viewStudentToCourses(int studentId) throws Exception;
}
