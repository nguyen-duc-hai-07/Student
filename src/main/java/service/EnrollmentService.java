package service;

import model.Course;
import model.Student;
import model.StudentCourse;
import java.util.*;

public interface EnrollmentService {
    void addStudent(Student student) throws Exception;

    void addCourse(Course course) throws Exception;

    void enrollCourse(int studentId , int courseId) throws Exception;

    List<StudentCourse> viewAllEnrollments() throws Exception;

    List<StudentCourse> viewStudentToCourses(int studentId) throws Exception;

    List<StudentCourse> viewCourseToStudents(int courseId) throws Exception;

    void cancelEnrollment(int id) throws Exception;
}
