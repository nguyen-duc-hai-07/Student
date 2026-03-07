package dao.impl;

import dao.StudentDAO;
import model.Course;
import model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
}
