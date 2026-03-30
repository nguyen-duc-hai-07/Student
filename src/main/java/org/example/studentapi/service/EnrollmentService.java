package org.example.studentapi.service;

import org.example.studentapi.dto.request.EnrollmentRequest;
import org.example.studentapi.dto.response.StudentCourseDTO;
import org.example.studentapi.model.StudentCourse;

import java.util.*;

public interface EnrollmentService {


    StudentCourseDTO enrollCourse(EnrollmentRequest quest) throws Exception;

    List<StudentCourseDTO> viewAllEnrollments() throws Exception;

    void cancelEnrollment(int id) throws Exception;

}
