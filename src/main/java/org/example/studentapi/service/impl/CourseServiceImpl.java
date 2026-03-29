package org.example.studentapi.service.impl;

import org.example.studentapi.config.DBConnectionPool;
import org.example.studentapi.dao.CourseDAO;
import org.example.studentapi.dto.response.CourseResponse;
import org.example.studentapi.dto.response.StudentResponse;
import org.example.studentapi.model.Course;
import org.springframework.stereotype.Service;
import org.example.studentapi.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseDAO courseDAO;
    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);
    public CourseServiceImpl( CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void addCourse(Course course) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();

            courseDAO.insert(conn, course);

            conn.commit();

            logger.info("Course added successfully: id={}", course.getId());
        } catch (Exception e) {
            logger.error("Failed to add course: {}", e.getMessage());
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

    public CourseResponse viewCourseToStudents(int courseId) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();

            Course course = courseDAO.findById(conn, courseId);
            if(course == null) {
                logger.warn("Course not found: id={}", courseId);
            }
            List<StudentResponse> studentCourses = courseDAO.findByCourse(conn, courseId);
            conn.commit();
            logger.info("Found {} students for course id={}", studentCourses.size(), courseId);
            return new CourseResponse(
                    course.getId(),
                    course.getName(),
                    course.getCredits(),
                    studentCourses
            );
        } catch (Exception e) {
            logger.error("Failed to fetch course students: {}", e.getMessage());
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

    public void deleteCourse(int id) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            Course course = courseDAO.findById(conn, id);
            if(course == null) {
                logger.warn("Course not found: id={}", id);
            }
            courseDAO.delete(conn, id);
            conn.commit();
            logger.info("Course deleted: id={}", id);
        } catch (Exception e) {
            logger.error("Failed to delete course id={}: {}", id, e.getMessage());
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

    public List<Course> findAllCourse() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            List<Course> courses = courseDAO.findAll(conn);
            conn.commit();
            logger.info("Found {} courses", courses.size());
            return courses;
        } catch (Exception e) {
            logger.error("Failed to fetch courses: {}", e.getMessage());
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
