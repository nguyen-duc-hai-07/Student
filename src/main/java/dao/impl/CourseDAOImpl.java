package dao.impl;

import dao.CourseDAO;
import dto.StudentResponse;
import model.Course;
import model.Student;

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

    public void delete(Connection conn, int id) throws Exception {
        String sql = "DELETE FROM courses WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<StudentResponse> findByCourse(Connection conn, int courseId) throws Exception {
        String sql = """
                SELECT s.id, s.name, s.email, s.phone
                FROM courses c
                JOIN student_course sc ON c.id = sc.course_id
                JOIN students s ON sc.student_id = s.id
                WHERE c.id = ?
                """;

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            List<StudentResponse> students = new ArrayList<>();
            while(rs.next()) {
                students.add(new StudentResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        null
                ));
            }
            return students;
        }
    }
}
