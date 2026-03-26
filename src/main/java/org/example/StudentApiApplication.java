package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"controller", "service", "config", "dao"})
public class StudentApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentApiApplication.class, args);
    }
}