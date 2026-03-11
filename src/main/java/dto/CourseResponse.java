package dto;

public class CourseResponse {
    private int id;
    private String name;
    private int credits;

    public CourseResponse(int id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public CourseResponse() {}
    public int getId()         { return id; }
    public String getName()    { return name; }
    public int getCredits()    { return credits; }

    @Override
    public String toString() {
        return "CourseResponse [id=" + id + ", name=" + name + ", credits=" + credits + "]";
    }
}
