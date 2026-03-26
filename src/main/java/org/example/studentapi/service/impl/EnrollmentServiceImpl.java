package org.example.studentapi.service.impl;

import org.example.studentapi.config.DBConnectionPool;
import org.example.studentapi.dao.CourseDAO;
import org.example.studentapi.dao.StudentCourseDAO;
import org.example.studentapi.dao.StudentDAO;
import org.example.studentapi.dto.StudentCourseDTO;
import org.example.studentapi.model.Course;
import org.example.studentapi.model.Student;
import org.example.studentapi.model.StudentCourse;
import org.springframework.stereotype.Service;
import org.example.studentapi.service.EnrollmentService;

import java.sql.Connection;
import java.util.*;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final CourseDAO courseDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final StudentDAO studentDAO;

    public EnrollmentServiceImpl( CourseDAO courseDAO, StudentCourseDAO studentCourseDAO, StudentDAO studentDAO) {
        this.courseDAO = courseDAO;
        this.studentCourseDAO = studentCourseDAO;
        this.studentDAO = studentDAO;
    }


    public void enrollCourse(int studentId , int courseId) throws Exception {

        Connection conn = null;

        try {
            conn = DBConnectionPool.getInstance().getConnection();

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
            conn = DBConnectionPool.getInstance().getConnection();
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
            conn = DBConnectionPool.getInstance().getConnection();

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
