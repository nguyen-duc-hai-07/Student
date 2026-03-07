package service.impl;

import config.DBConnectionPool;
import dao.CourseDAO;
import dao.StudentCourseDAO;
import dao.StudentDAO;
import dao.impl.CourseDAOImpl;
import dao.impl.StudentCourseDAOImpl;
import dao.impl.StudentDAOImpl;
import model.Course;
import model.Student;
import model.StudentCourse;
import service.EnrollmentService;

import java.sql.Connection;
import java.util.*;

public class EnrollmentServiceImpl implements EnrollmentService {

    private final DBConnectionPool pool = DBConnectionPool.getInstance();
    private final CourseDAO courseDAO = new CourseDAOImpl();
    private final StudentCourseDAO studentCourseDAO = new StudentCourseDAOImpl();
    private final StudentDAO studentDAO = new StudentDAOImpl();

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
            conn.close();
        }
    }

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
            conn.close();
        }
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
            conn.close();
        }
    }

    public List<StudentCourse> viewAllEnrollments() throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<StudentCourse> studentCourses = studentCourseDAO.findAll(conn);
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
            conn.close();
        }
    }

    public List<StudentCourse> viewStudentToCourses(int studentId) throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<StudentCourse> studentCourses = studentCourseDAO.findByStudent(conn, studentId);
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
            conn.close();
        }
    }

    public List<StudentCourse> viewCourseToStudents(int courseId) throws Exception {
        Connection conn = null;
        try {
            conn = pool.getConnection();
            List<StudentCourse> studentCourses = studentCourseDAO.findByCourse(conn, courseId);
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
            conn.close();
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
            conn.close();
        }
    }
}
