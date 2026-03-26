package org.example.studentapi.service;

import org.example.studentapi.dto.StudentCourseDTO;

import java.util.*;

public interface EnrollmentService {


    void enrollCourse(int studentId , int courseId) throws Exception;

    List<StudentCourseDTO> viewAllEnrollments() throws Exception;

    void cancelEnrollment(int id) throws Exception;

}
