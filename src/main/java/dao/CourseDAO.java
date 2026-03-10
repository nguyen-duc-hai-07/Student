package dao;

import model.Course;
import model.Student;

import java.sql.Connection;
import java.util.List;

public interface CourseDAO {

    void insert(Connection conn, Course course) throws Exception;

    Course findById(Connection conn, int id) throws Exception;

    List<Course> findAll(Connection conn) throws Exception;

    void delete(Connection conn, int id) throws Exception;

    List<Student> findByCourse(Connection conn, int courseId) throws Exception;
}
