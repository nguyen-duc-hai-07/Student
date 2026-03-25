package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.DBConnectionPool;
import dao.impl.StudentDAOImpl;
import dto.StudentResponse;
import model.Student;
import service.StudentService;
import service.impl.StudentServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/students/*")
public class StudentController extends HttpServlet {

    private StudentService studentService;
    private ObjectMapper mapper;

    @Override
    public void init() {
        DBConnectionPool pool = DBConnectionPool.getInstance();
        this.studentService = new StudentServiceImpl(pool, new StudentDAOImpl());
        this.mapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Student> students = studentService.findAllStudent();
                resp.getWriter().write(mapper.writeValueAsString(students));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                StudentResponse sr = studentService.viewStudentToCourses(id);
                resp.getWriter().write(mapper.writeValueAsString(sr));
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
            Student student = mapper.readValue(req.getInputStream(), Student.class);
            studentService.addStudent(student);
            resp.setStatus(201);
            resp.getWriter().write(mapper.writeValueAsString(student));
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
            studentService.deleteStudent(id);
            resp.getWriter().write("{\"message\":\"Deleted successfully\"}");
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}