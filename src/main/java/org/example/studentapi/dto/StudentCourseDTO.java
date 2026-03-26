package org.example.studentapi.dto;

public class StudentCourseDTO {
    private int id;
    private String studentName;
    private String courseName;
    private int credits;
    public StudentCourseDTO(String studentName, String courseName, int credits) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.credits = credits;
    }
    public StudentCourseDTO() {}

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "StudentCourseDTO [id=" + id + ", studentName=" + studentName
                + ", courseName=" + courseName + ", credits=" + credits + "]";
    }
}
