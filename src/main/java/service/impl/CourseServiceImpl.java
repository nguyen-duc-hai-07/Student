package service.impl;

import config.DBConnectionPool;
import dao.CourseDAO;
import dao.impl.CourseDAOImpl;
import dto.CourseResponse;
import dto.StudentResponse;
import model.Course;
import service.CourseService;

import java.sql.Connection;
import java.util.List;

public class CourseServiceImpl implements CourseService {
    private final DBConnectionPool pool = DBConnectionPool.getInstance();
    private final CourseDAO courseDAO = new CourseDAOImpl();

    public void addCourse(Course course) throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();

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
            conn = pool.getConnection();

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
            conn = pool.getConnection();
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
            conn = pool.getConnection();
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
