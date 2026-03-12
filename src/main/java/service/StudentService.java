package service;

import dto.CourseResponse;
import model.Student;

import java.util.List;

public interface StudentService {

    void addStudent(Student student) throws Exception;

    void deleteStudent(int id) throws Exception;

    List<Student> findAllStudent() throws Exception;

    List<CourseResponse> viewStudentToCourses(int studentId) throws Exception;
}
