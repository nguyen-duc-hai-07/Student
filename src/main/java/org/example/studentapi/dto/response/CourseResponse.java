package org.example.studentapi.dto.response;

import java.util.*;

public class CourseResponse {
    private int id;
    private String name;
    private int credits;
    private List<StudentResponse> students;
    public CourseResponse(int id, String name, int credits , List<StudentResponse> students) {
        this.id = id;
        this.name = name;
        this.credits = credits;
        this.students = students;
    }

    public CourseResponse() {}
    public int getId()         { return id; }
    public String getName()    { return name; }
    public int getCredits()    { return credits; }
    public List<StudentResponse> getStudents() { return students; }
    public void setStudents(List<StudentResponse> students) { this.students = students; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setStudent(StudentResponse student) { this.students.add(student); }
    @Override

    public String toString() {
        return "CourseResponse [id=" + id + ", name=" + name
                + ", credits=" + credits
                + ", students=" + students + "]";
    }
}
