package service.impl;

import config.DBConnectionPool;
import dao.StudentDAO;
import dao.impl.StudentDAOImpl;
import dto.CourseResponse;
import dto.StudentResponse;
import model.Student;
import service.StudentService;

import java.sql.Connection;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final DBConnectionPool pool;
    private final StudentDAO studentDAO;

    public StudentServiceImpl(DBConnectionPool pool, StudentDAO studentDAO) {
        this.pool = pool;
        this.studentDAO = studentDAO;
    }

    public void addStudent(Student student) throws Exception {
        Connection conn = null;

        try {
            conn = pool.getConnection();

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
            conn = pool.getConnection();

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
            conn = pool.getConnection();
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
            conn = pool.getConnection();
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
