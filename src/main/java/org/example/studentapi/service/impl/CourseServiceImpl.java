package org.example.studentapi.service.impl;

import org.example.studentapi.config.DBConnectionPool;
import org.example.studentapi.dao.CourseDAO;
import org.example.studentapi.dto.CourseResponse;
import org.example.studentapi.dto.StudentResponse;
import org.example.studentapi.model.Course;
import org.springframework.stereotype.Service;
import org.example.studentapi.service.CourseService;

import java.sql.Connection;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseDAO courseDAO;

    public CourseServiceImpl( CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    public void addCourse(Course course) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();

            courseDAO.insert(conn, course);

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

    public CourseResponse viewCourseToStudents(int courseId) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();

            Course course = courseDAO.findById(conn, courseId);
            if(course == null) {
                throw new Exception("Course not found");
            }

            List<StudentResponse> studentCourses = courseDAO.findByCourse(conn, courseId);
            conn.commit();
            return new CourseResponse(
                    course.getId(),
                    course.getName(),
                    course.getCredits(),
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

    public void deleteCourse(int id) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            courseDAO.delete(conn, id);
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

    public List<Course> findAllCourse() throws Exception {
        Connection conn = null;
        try {
            conn = DBConnectionPool.getInstance().getConnection();
            List<Course> courses = courseDAO.findAll(conn);
            conn.commit();
            return courses;
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
