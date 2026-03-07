package dao;

import model.Course;

import java.sql.Connection;
import java.util.List;

public interface CourseDAO {

    void insert(Connection conn, Course course) throws Exception;

    Course findById(Connection conn, int id) throws Exception;

    List<Course> findAll(Connection conn) throws Exception;

}
