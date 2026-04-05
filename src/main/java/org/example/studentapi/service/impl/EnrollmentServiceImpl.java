package org.example.studentapi.service.impl;

import lombok.extern.slf4j.Slf4j;
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
import java.sql.Connection;
import java.util.*;

@Slf4j
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


    public StudentCourseDTO enrollCourse(EnrollmentRequest quest) throws Exception {
        log.info("Enrolling student: studentId={}, courseId={}", quest.getStudentId(), quest.getCourseId());
        Connection conn = null;

        try {
            conn = DBConnectionPool.getInstance().getConnection();

            Student student = studentDAO.findById(conn, quest.getStudentId());
            if(student == null) {
                log.warn("Student not found: id={}", quest.getStudentId());
                throw new Exception("Student not found");
            }

            Course course = courseDAO.findById(conn, quest.getCourseId());
            if(course == null) {
                log.warn("Course not found: id={}", quest.getCourseId() );
                throw new Exception("Course not found");
            }

            StudentCourse studentCourse = new StudentCourse(quest.getStudentId(), quest.getCourseId());
            studentCourseDAO.insert(conn,studentCourse);

            conn.commit();
            log.info("Student enrolled in course: studentId={}, courseId={}", quest.getStudentId(), quest.getCourseId());
            return new StudentCourseDTO(
                    studentCourse.getId(),
                    student.getName(),
                    course.getName(),
                    course.getCredits()
            );
        } catch (Exception e) {
            log.error("Failed to enroll student in course: studentId={}, courseId={}", quest.getStudentId(), quest.getCourseId(),e);
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
        log.info("Fetching all enrollments");
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            List<StudentCourseDTO> studentCourses = studentCourseDAO.findAll(conn);
            conn.commit();
            log.info("Found {} enrollments", studentCourses.size());
            return studentCourses;
        } catch (Exception e) {
            log.error("Failed to fetch enrollments: {}", e.getMessage(),e);
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
        log.info("Canceling enrollment id={}", id);
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();

            StudentCourse studentCourse = studentCourseDAO.findById(conn, id);
            if(studentCourse == null) {
                log.warn("Enrollment not found: id={}", id);
                throw new Exception("Enrollment not found");
            }

            studentCourseDAO.delete(conn, id);

            conn.commit();

            log.info("Enrollment cancelled: id={}", id);
        } catch (Exception e) {
            log.error("Failed to cancel enrollment id={}: {}", id, e.getMessage(), e);
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
