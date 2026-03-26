package org.example.studentapi.service.impl;

import org.example.studentapi.config.DBConnectionPool;
import org.example.studentapi.dao.StudentDAO;
import org.example.studentapi.dto.CourseResponse;
import org.example.studentapi.dto.StudentResponse;
import org.example.studentapi.model.Student;
import org.springframework.stereotype.Service;
import org.example.studentapi.service.StudentService;

import java.sql.Connection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentDAO studentDAO;

    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public void addStudent(Student student) throws Exception {
        Connection conn = null;

        try {
            conn = DBConnectionPool.getInstance().getConnection();

            studentDAO.insert(conn, student);

            conn.commit();
        } catch (Exception e) {
            try {
                if(conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw e;
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
    }

    public StudentResponse viewStudentToCourses(int studentId) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();

            Student student = studentDAO.findById(conn, studentId);
            if(student == null) {
                throw new Exception("Student not found");
            }

            List<CourseResponse> studentCourses = studentDAO.findByStudent(conn, studentId);

            conn.commit();

            return new StudentResponse(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getPhone(),
                    studentCourses
            );

        } catch (Exception e) {
            try {
                if(conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw e;
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
    }

    public void deleteStudent(int id) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            studentDAO.delete(conn, id);
            conn.commit();
        } catch (Exception e) {
            try {
                if(conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw e;
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
    }

    public List<Student> findAllStudent() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            List<Student> students = studentDAO.findAll(conn);
            conn.commit();
            return students;
        } catch (Exception e) {
            try {
                if(conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw e;
        } finally {
            if(conn != null) {
                conn.close();
            }
        }
    }
}
