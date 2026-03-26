package dto;

public class EnrollmentRequest {
    public int studentId;
    public int courseId;
    public EnrollmentRequest(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }
    public EnrollmentRequest() {}

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
