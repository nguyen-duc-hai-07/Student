package org.example.studentapi.dao.impl;

import org.example.studentapi.dao.StudentCourseDAO;
import org.example.studentapi.dto.response.StudentCourseDTO;
import org.example.studentapi.model.StudentCourse;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentCourseDAOImpl implements StudentCourseDAO {
    public void insert (Connection conn, StudentCourse studentCourse) throws Exception {
        String sql = "INSERT INTO student_course (student_id, course_id) VALUES (?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, studentCourse.getStudentId());
            ps.setInt(2, studentCourse.getCourseId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                studentCourse.setId(rs.getInt(1));
            }
        }
    }

    public StudentCourse findById(Connection conn, int id) throws Exception {
        String sql = "SELECT * FROM student_course WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new StudentCourse(
                        rs.getInt("id"),
                        rs.getInt("student_id"),
                        rs.getInt("course_id"));
            }
        }
        return null;
    }

    public List<StudentCourseDTO> findAll(Connection conn) throws Exception {
        String sql = """
                         SELECT sc.id , s.name AS studentName, c.name AS courseName, c.credits 
                         FROM student_course sc
                         JOIN students s ON sc.student_id = s.id
                         JOIN courses c ON sc.course_id = c.id
                         """;
        List<StudentCourseDTO> studentCourseDTOs = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                StudentCourseDTO studentCourseDTO = new StudentCourseDTO();
                studentCourseDTO.setId(rs.getInt("id"));
                studentCourseDTO.setStudentName(rs.getString("studentName"));
                studentCourseDTO.setCourseName(rs.getString("courseName"));
                studentCourseDTO.setCredits(rs.getInt("credits"));
                studentCourseDTOs.add(studentCourseDTO);
            }
            return studentCourseDTOs;
        }
    }

    public void delete(Connection conn, int id) throws Exception {
        String sql = "DELETE FROM student_course WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
