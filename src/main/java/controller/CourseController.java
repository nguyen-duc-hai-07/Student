package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.DBConnectionPool;
import dao.impl.CourseDAOImpl;
import dto.CourseResponse;
import model.Course;
import service.CourseService;
import service.impl.CourseServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/v1/courses/*")
public class CourseController extends HttpServlet {

    private CourseService courseService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        DBConnectionPool pool = DBConnectionPool.getInstance();
        this.courseService = new CourseServiceImpl(pool, new CourseDAOImpl());
        this.mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo();
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Course> courses = courseService.findAllCourse();
                resp.getWriter().write(mapper.writeValueAsString(courses));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                CourseResponse cr = courseService.viewCourseToStudents(id);
                resp.getWriter().write(mapper.writeValueAsString(cr));
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        try {
            Course course = mapper.readValue(req.getInputStream(), Course.class);
            courseService.addCourse(course);
            resp.setStatus(201);
            resp.getWriter().write(mapper.writeValueAsString(course));
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
            courseService.deleteCourse(id);
            resp.getWriter().write("{\"message\":\"Deleted successfully\"}");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}

