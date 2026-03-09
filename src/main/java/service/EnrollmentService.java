package service;

import dto.StudentCourseDTO;
import model.Course;
import model.Student;
import model.StudentCourse;
import java.util.*;

public interface EnrollmentService {
    void addStudent(Student student) throws Exception;

    void addCourse(Course course) throws Exception;

    void enrollCourse(int studentId , int courseId) throws Exception;

    List<StudentCourseDTO> viewAllEnrollments() throws Exception;

    List<StudentCourseDTO> viewStudentToCourses(int studentId) throws Exception;

    List<StudentCourseDTO> viewCourseToStudents(int courseId) throws Exception;

    void cancelEnrollment(int id) throws Exception;
}
