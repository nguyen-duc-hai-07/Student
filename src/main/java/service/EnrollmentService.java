package service;

import dto.CourseResponse;
import dto.StudentCourseDTO;
import dto.StudentResponse;
import model.Course;
import model.Student;
import model.StudentCourse;
import java.util.*;

public interface EnrollmentService {


    void enrollCourse(int studentId , int courseId) throws Exception;

    List<StudentCourseDTO> viewAllEnrollments() throws Exception;

    void cancelEnrollment(int id) throws Exception;

}
