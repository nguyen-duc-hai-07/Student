package dao.impl;

import dao.StudentCourseDAO;
import model.StudentCourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public List<StudentCourse> findAll(Connection conn) throws Exception {
        String sql = """
                         SELECT sc.id , s.name AS studentName, c.name AS courseName, c.credits 
                         FROM student_course sc
                         JOIN students s ON sc.student_id = s.id
                         JOIN courses c ON sc.course_id = c.id
                         """;
        List<StudentCourse> studentCourses = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setId(rs.getInt("id"));
                studentCourse.setStudentName(rs.getString("studentName"));
                studentCourse.setCourseName(rs.getString("courseName"));
                studentCourse.setCredits(rs.getString("credits"));
                studentCourses.add(studentCourse);
            }
            return studentCourses;
        }
    }


    public List<StudentCourse> findByStudent(Connection conn , int studentId) throws Exception {
        List<StudentCourse> studentCourses = new ArrayList<>();
        String sql = """
                SELECT sc.id , s.name AS studentName, c.name AS courseName, c.credits 
                FROM student_course sc
                JOIN students s ON sc.student_id = s.id
                JOIN courses c ON sc.course_id = c.id
                WHERE sc.student_id = ?
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setId(rs.getInt("id"));
                studentCourse.setStudentName(rs.getString("studentName"));
                studentCourse.setCourseName(rs.getString("courseName"));
                studentCourse.setCredits(rs.getString("credits"));
                studentCourses.add(studentCourse);
            }
            return studentCourses;
        }
    }

    public List<StudentCourse> findByCourse(Connection conn , int CourseId) throws Exception {
        List<StudentCourse> studentCourses = new ArrayList<>();
        String sql = """
                SELECT sc.id , s.name AS studentName, c.name AS courseName, c.credits 
                FROM student_course sc
                JOIN students s ON sc.student_id = s.id
                JOIN courses c ON sc.course_id = c.id
                WHERE sc.course_id = ?
                """;

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, CourseId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                StudentCourse studentCourse = new StudentCourse();
                studentCourse.setId(rs.getInt("id"));
                studentCourse.setStudentName(rs.getString("studentName"));
                studentCourse.setCourseName(rs.getString("courseName"));
                studentCourse.setCredits(rs.getString("credits"));
                studentCourses.add(studentCourse);
            }
            return studentCourses;
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
