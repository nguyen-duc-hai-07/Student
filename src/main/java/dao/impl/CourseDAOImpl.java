package dao.impl;

import dao.CourseDAO;
import model.Course;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CourseDAOImpl implements CourseDAO {
    public void insert(Connection conn, Course course) throws Exception {
        String sql = "INSERT INTO courses (name, credits) VALUES (?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, course.getName());
            ps.setInt(2, course.getCredits());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                course.setId(rs.getInt(1));
            }
        }
    }

    public Course findById(Connection conn, int id) throws Exception {
        String sql = "SELECT * FROM courses WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("credits")
                );
            }
        }
        return null;
    }
    public List<Course> findAll(Connection conn) throws Exception {
        String sql = "SELECT * FROM courses";
        List<Course> courses = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("credits")
                ));
            }
            return courses;
        }
    }
}
