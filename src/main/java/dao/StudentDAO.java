package dao;

import model.Course;
import model.Student;

import java.util.*;
import java.sql.Connection;

public interface StudentDAO {

    void insert (Connection conn, Student student) throws Exception;

    Student findById(Connection conn, int id) throws Exception;

    List<Student> findAll(Connection conn) throws Exception;

    void delete(Connection conn, int id) throws Exception;

    List<Course> findByStudent(Connection conn, int studentId) throws Exception;
}
