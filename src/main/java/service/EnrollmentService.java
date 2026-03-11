package service;

import dto.CourseResponse;
import dto.StudentCourseDTO;
import dto.StudentResponse;
import model.Course;
import model.Student;
import model.StudentCourse;
import java.util.*;

public interface EnrollmentService {
    void addStudent(Student student) throws Exception;

    void addCourse(Course course) throws Exception;

    void enrollCourse(int studentId , int courseId) throws Exception;

    List<StudentCourseDTO> viewAllEnrollments() throws Exception;

    List<CourseResponse> viewStudentToCourses(int studentId) throws Exception;

    List<StudentResponse> viewCourseToStudents(int courseId) throws Exception;

    void cancelEnrollment(int id) throws Exception;

    void deleteStudent(int id) throws Exception;

    void deleteCourse(int id) throws Exception;

    List<Student> findAllStudent() throws Exception;

    List<Course> findAllCourse() throws Exception;
}
