package org.example.studentapi.dto.request;

public class CourseRequest {
    private String name;
    private int credits;
    public CourseRequest(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }
    public CourseRequest() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCredits() {
        return credits;
    }
    public void setCredits(int credits) {
        this.credits = credits;
    }
    public CourseRequest(String name) {
        this.name = name;
    }
    public CourseRequest(int credits) {
        this.credits = credits;
    }
}
