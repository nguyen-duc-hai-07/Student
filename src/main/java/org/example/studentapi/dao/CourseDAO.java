package org.example.studentapi.dao;

import org.example.studentapi.dto.response.CourseResponse;
import org.example.studentapi.dto.response.StudentResponse;
import org.example.studentapi.model.Course;

import java.sql.Connection;
import java.util.List;

public interface CourseDAO {

    void insert(Connection conn, Course course) throws Exception;

    Course findById(Connection conn, int id) throws Exception;

    List<CourseResponse> findAll(Connection conn) throws Exception;

    void delete(Connection conn, int id) throws Exception;

    List<StudentResponse> findByCourse(Connection conn, int courseId) throws Exception;
}
