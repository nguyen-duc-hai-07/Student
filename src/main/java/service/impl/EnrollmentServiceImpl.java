package service.impl;

import config.DBConnectionPool;
import dao.CourseDAO;
import dao.StudentCourseDAO;
import dao.StudentDAO;
import dao.impl.CourseDAOImpl;
import dao.impl.StudentCourseDAOImpl;
import dao.impl.StudentDAOImpl;
import dto.CourseResponse;
import dto.StudentCourseDTO;
import dto.StudentResponse;
import model.Course;
import model.Student;
import model.StudentCourse;
import org.springframework.stereotype.Service;
import service.EnrollmentService;

import java.sql.Connection;
import java.util.*;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final DBConnectionPool pool;
    private final CourseDAO courseDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final StudentDAO studentDAO;

    public EnrollmentServiceImpl(DBConnectionPool pool, CourseDAO courseDAO, StudentCourseDAO studentCourseDAO, StudentDAO studentDAO) {
        this.pool = pool;
        this.courseDAO = courseDAO;
        this.studentCourseDAO = studentCourseDAO;
        this.studentDAO = studentDAO;
    }


    public void enrollCourse(int studentId , int courseId) throws Exception {

        Connection conn = null;

        try {
            conn = pool.getConnection();

            Student student = studentDAO.findById(conn, studentId);
            if(student == null) {
                throw new Exception("Student not found");
            }

            Course course = courseDAO.findById(conn, courseId);
            if(course == null) {
                throw new Exception("Course not found");
            }

            StudentCourse studentCourse = new StudentCourse(studentId, courseId);
            studentCourseDAO.insert(conn,studentCourse);

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

    public List<StudentCourseDTO> viewAllEnrollments() throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<StudentCourseDTO> studentCourses = studentCourseDAO.findAll(conn);
            conn.commit();
            return studentCourses;
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

    public void cancelEnrollment(int id) throws Exception {
        Connection conn = null;

        try {
            conn = pool.getConnection();

            StudentCourse studentCourse = studentCourseDAO.findById(conn, id);
            if(studentCourse == null) {
                throw new Exception("Student Course not found");
            }

            studentCourseDAO.delete(conn, id);

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

}
