package org.example.studentapi.service.impl;

import org.example.studentapi.config.DBConnectionPool;
import org.example.studentapi.dao.CourseDAO;
import org.example.studentapi.dao.StudentCourseDAO;
import org.example.studentapi.dao.StudentDAO;
import org.example.studentapi.dto.request.EnrollmentRequest;
import org.example.studentapi.dto.response.StudentCourseDTO;
import org.example.studentapi.model.Course;
import org.example.studentapi.model.Student;
import org.example.studentapi.model.StudentCourse;
import org.springframework.stereotype.Service;
import org.example.studentapi.service.EnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.util.*;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);
    private final CourseDAO courseDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final StudentDAO studentDAO;

    public EnrollmentServiceImpl( CourseDAO courseDAO, StudentCourseDAO studentCourseDAO, StudentDAO studentDAO) {
        this.courseDAO = courseDAO;
        this.studentCourseDAO = studentCourseDAO;
        this.studentDAO = studentDAO;
    }


    public StudentCourseDTO enrollCourse(EnrollmentRequest quest) throws Exception {

        Connection conn = null;

        try {
            conn = DBConnectionPool.getInstance().getConnection();

            Student student = studentDAO.findById(conn, quest.getStudentId());
            if(student == null) {
                logger.warn("Student not found: id={}", quest.getStudentId());
                throw new Exception("Student not found");
            }

            Course course = courseDAO.findById(conn, quest.getCourseId());
            if(course == null) {
                logger.warn("Course not found: id={}", quest.getCourseId() );
                throw new Exception("Course not found");
            }

            StudentCourse studentCourse = new StudentCourse(quest.getStudentId(), quest.getCourseId());
            studentCourseDAO.insert(conn,studentCourse);

            conn.commit();
            logger.info("Student enrolled in course: studentId={}, courseId={}", quest.getStudentId(), quest.getCourseId());
            return new StudentCourseDTO(
                    studentCourse.getId(),
                    student.getName(),
                    course.getName(),
                    course.getCredits()
            );
        } catch (Exception e) {
            logger.error("Failed to enroll student in course: studentId={}, courseId={}", quest.getStudentId(), quest.getCourseId(),e);
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
            logger.info("Found {} enrollments", studentCourses.size());
            return studentCourses;
        } catch (Exception e) {
            logger.error("Failed to fetch enrollments: {}", e.getMessage());
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
                logger.warn("Enrollment not found: id={}", id);
            }

            studentCourseDAO.delete(conn, id);

            conn.commit();

            logger.info("Enrollment cancelled: id={}", id);
        } catch (Exception e) {
            logger.error("Failed to cancel enrollment id={}: {}", id, e.getMessage());
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
