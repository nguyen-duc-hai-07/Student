package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.DBConnectionPool;
import dao.impl.CourseDAOImpl;
import dao.impl.StudentCourseDAOImpl;
import dao.impl.StudentDAOImpl;
import dto.StudentCourseDTO;
import service.EnrollmentService;
import service.impl.EnrollmentServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/api/enrollments/*")
public class EnrollmentController extends HttpServlet {

    private EnrollmentService enrollmentService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        DBConnectionPool pool = DBConnectionPool.getInstance();
        this.enrollmentService = new EnrollmentServiceImpl(
                pool, new CourseDAOImpl(), new StudentCourseDAOImpl(), new StudentDAOImpl()
        );
        this.mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try {
            List<StudentCourseDTO> list = enrollmentService.viewAllEnrollments();
            resp.getWriter().write(mapper.writeValueAsString(list));
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try {
            Map<String, Integer> body = mapper.readValue(req.getInputStream(), Map.class);
            enrollmentService.enrollCourse(body.get("studentId"), body.get("courseId"));
            resp.setStatus(201);
            resp.getWriter().write("{\"message\":\"Enrolled successfully\"}");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try {
            int id = Integer.parseInt(req.getPathInfo().substring(1));
            enrollmentService.cancelEnrollment(id);
            resp.getWriter().write("{\"message\":\"Cancelled successfully\"}");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}