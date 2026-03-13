package dto;

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
    @Override

    public String toString() {
        return "CourseResponse [id=" + id + ", name=" + name
                + ", credits=" + credits
                + ", students=" + students + "]";
    }
}
