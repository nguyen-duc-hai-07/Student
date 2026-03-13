package service;

import dto.CourseResponse;
import dto.StudentResponse;
import model.Course;

import java.util.List;

public interface CourseService {
    void addCourse(Course course) throws Exception;

    CourseResponse viewCourseToStudents(int courseId) throws Exception;

    void deleteCourse(int id) throws Exception;

    List<Course> findAllCourse() throws Exception;
}
