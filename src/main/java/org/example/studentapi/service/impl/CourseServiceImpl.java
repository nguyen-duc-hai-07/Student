package org.example.studentapi.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.studentapi.config.DBConnectionPool;
import org.example.studentapi.dao.CourseDAO;
import org.example.studentapi.dto.request.CourseRequest;
import org.example.studentapi.dto.response.CourseResponse;
import org.example.studentapi.dto.response.StudentResponse;
import org.example.studentapi.model.Course;
import org.springframework.stereotype.Service;
import org.example.studentapi.service.CourseService;
import java.sql.Connection;
import java.util.List;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseDAO courseDAO;
    public CourseServiceImpl( CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public CourseResponse addCourse(CourseRequest quest) throws Exception {
        Course course = new Course(quest.getName(), quest.getCredits());
        log.info("Adding course: name={}, credits={}", quest.getName(), quest.getCredits());
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();

            courseDAO.insert(conn, course);

            conn.commit();

            log.info("Course added successfully: id={}", course.getId());

            return new CourseResponse(course.getId(), course.getName(), course.getCredits(), null);
        } catch (Exception e) {
            log.error("Failed to add course: {}", e.getMessage(), e);
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
        log.info("Fetching students for courseId={}", courseId);
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();

            Course course = courseDAO.findById(conn, courseId);
            if(course == null) {
                log.warn("Course not found: id={}", courseId);
                throw new Exception("Course not found");
            }
            List<StudentResponse> studentCourses = courseDAO.findByCourse(conn, courseId);
            conn.commit();
            log.info("Found {} students for course id={}", studentCourses.size(), courseId);
            return new CourseResponse(
                    course.getId(),
                    course.getName(),
                    course.getCredits(),
                    studentCourses
            );
        } catch (Exception e) {
            log.error("Failed to fetch course students: {}", e.getMessage(),e);
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
        log.info("Deleting course id={}", id);
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            Course course = courseDAO.findById(conn, id);
            if(course == null) {
                log.warn("Course not found: id={}", id);
                throw new Exception("Course not found");
            }
            courseDAO.delete(conn, id);
            conn.commit();
            log.info("Course deleted: id={}", id);
        } catch (Exception e) {
            log.error("Failed to delete course id={}: {}", id, e.getMessage(),e);
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

    public List<CourseResponse> findAllCourse() throws Exception {
        log.info("Fetching all courses");
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            List<CourseResponse> courses = courseDAO.findAll(conn);
            conn.commit();
            log.info("Found {} courses", courses.size());
            return courses;
        } catch (Exception e) {
            log.error("Failed to fetch courses: {}", e.getMessage(),e);
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
