package org.example.studentapi.dao.impl;

import org.example.studentapi.dao.StudentDAO;
import org.example.studentapi.dto.response.CourseResponse;
import org.example.studentapi.model.Student;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {
    public void insert (Connection conn, Student student) throws Exception {
        String sql = "INSERT INTO students (name, email, phone) VALUES (?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(sql , Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                student.setId(rs.getInt(1));
            }
        }
    }

    public Student findById(Connection conn, int id) throws Exception {
        String sql = "SELECT * FROM students WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"));
            }
            return null;
        }
    }

    public List<Student> findAll(Connection conn) throws Exception {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")));
            }
        }
        return students;
    }

    public void delete(Connection conn, int id) throws Exception {
        String sql = "DELETE FROM students WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<CourseResponse> findByStudent(Connection conn, int studentId) throws Exception {
        String sql = """
                SELECT c.id, c.name, c.credits
                FROM students s
                JOIN student_course sc ON s.id = sc.student_id
                JOIN courses c ON sc.course_id = c.id
                WHERE s.id = ?
                """;

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            List<CourseResponse> courses = new ArrayList<>();
            while(rs.next()) {
                courses.add(new CourseResponse(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("credits"),
                        null
                ));
            }
            return courses;
        }
    }
}
