package org.example.studentapi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.studentapi.config.DBConnectionPool;
import org.example.studentapi.dao.StudentDAO;
import org.example.studentapi.dto.request.StudentRequest;
import org.example.studentapi.dto.response.CourseResponse;
import org.example.studentapi.dto.response.StudentResponse;
import org.example.studentapi.model.Student;
import org.springframework.stereotype.Service;
import org.example.studentapi.service.StudentService;

import java.sql.Connection;
import java.util.List;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public StudentResponse addStudent(StudentRequest quest) throws Exception {
        Student student = new Student(quest.getName(), quest.getEmail(), quest.getPhone());
        log.info("Adding student: name={}, email={}, phone={}", quest.getName(), quest.getEmail(), quest.getPhone());
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            studentDAO.insert(conn, student);
            conn.commit();
            log.info("Student added successfully: id={}", student.getId());
            return new StudentResponse(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getPhone(),
                    null
            );
        } catch (Exception e) {
            log.error("Failed to add student: {}", e.getMessage(),e);
            try { if(conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            throw e;
        } finally {
            if(conn != null) conn.close();
        }
    }

    public List<StudentResponse> findAllStudent() throws Exception {
        log.info("Fetching all students");
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            List<StudentResponse> students = studentDAO.findAll(conn);
            conn.commit();
            log.info("Found {} students", students.size());
            return students;
        } catch (Exception e) {
            log.error("Failed to fetch students: {}", e.getMessage(),e);
            try { if(conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            throw e;
        } finally {
            if(conn != null) conn.close();
        }
    }

    public void deleteStudent(int id) throws Exception {
        log.info("Deleting student id={}", id);
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            Student student = studentDAO.findById(conn, id);
            if(student == null) {
                log.warn("Student not found: id={}", id);
                throw new Exception("Student not found");
            }
            studentDAO.delete(conn, id);
            conn.commit();
            log.info("Student deleted: id={}", id);
        } catch (Exception e) {
            log.error("Failed to delete student id={}: {}", id, e.getMessage(), e);
            try { if(conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            throw e;
        } finally {
            if(conn != null) conn.close();
        }
    }

    public StudentResponse viewStudentToCourses(int studentId) throws Exception {
        log.info("Fetching courses for student id={}", studentId);
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            Student student = studentDAO.findById(conn, studentId);
            if(student == null) {
                log.warn("Student not found: id={}", studentId);
                throw new Exception("Student not found");
            }
            List<CourseResponse> studentCourses = studentDAO.findByStudent(conn, studentId);
            conn.commit();
            log.info("Found {} courses for student id={}", studentCourses.size(), studentId);
            return new StudentResponse(student.getId(), student.getName(),
                    student.getEmail(), student.getPhone(), studentCourses);
        } catch (Exception e) {
            log.error("Failed to fetch student courses: {}", e.getMessage(),e);
            try { if(conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            throw e;
        } finally {
            if(conn != null) conn.close();
        }
    }
}