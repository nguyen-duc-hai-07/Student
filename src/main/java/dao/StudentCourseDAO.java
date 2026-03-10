package dao;

import dto.StudentCourseDTO;
import model.Course;
import model.StudentCourse;

import java.util.*;
import java.sql.Connection;

public interface StudentCourseDAO {

    void insert (Connection conn, StudentCourse studentCourse) throws Exception;

    StudentCourse findById(Connection conn, int id) throws Exception;

    List<StudentCourseDTO> findAll(Connection conn) throws Exception;

    void delete(Connection conn, int id) throws Exception;

}
