package dto;

import java.util.*;

public class StudentResponse {
    private int id;
    private String name;
    private String email;
    private String phone;
    private List<CourseResponse> courses;
    public StudentResponse(int id, String name, String email, String phone , List<CourseResponse> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.courses = courses;
    }

    public int getId()          { return id; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public String getPhone()    { return phone; }
    public List<CourseResponse> getCourses() { return courses; }
    @Override
    public String toString() {
        return "StudentResponse [id=" + id + ", name=" + name
                + ", email=" + email + ", phone=" + phone
                + ", courses=" + courses + "]";
    }
}
