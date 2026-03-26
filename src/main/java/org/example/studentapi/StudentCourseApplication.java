package org.example.studentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
        exclude = { DataSourceAutoConfiguration.class }
)
public class StudentCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentCourseApplication.class, args);
    }
}