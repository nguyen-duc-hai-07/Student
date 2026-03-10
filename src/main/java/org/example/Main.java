package org.example;

import dto.StudentCourseDTO;
import model.Course;
import model.Student;
import model.StudentCourse;
import service.EnrollmentService;
import service.impl.EnrollmentServiceImpl;

import java.util.*;

public class Main {
     private static final EnrollmentService enrollmentService = new EnrollmentServiceImpl();
     private static final Scanner sc = new Scanner(System.in);

     public static void main(String[] args) throws Exception {
         while(true) {
             System.out.println("1. Add Student");
             System.out.println("2. Add Course");
             System.out.println("3. Enroll Course");
             System.out.println("4. View all");
             System.out.println("5. View courses by student");
             System.out.println("6. View students by course");
             System.out.println("7. Cancel enrollment");
             System.out.println("8. delete student");
             System.out.println("9. delete course");
             System.out.println("0. Exit");
             System.out.println("Nhập lựa chọn: ");
             int choice = sc.nextInt();
             sc.nextLine();

             switch (choice) {
                 case 1:
                     AddStudent();
                     break;
                 case 2:
                     AddCourse();
                     break;
                 case 3:
                     EnrollCourse();
                     break;
                 case 4:
                     ViewAll();
                     break;
                 case 5:
                     ViewStudentToCourses();
                     break;
                 case 6:
                     ViewCourseToStudents();
                     break;
                 case 7:
                     Cancel();
                     break;
                 case 8:
                     DeleteStudent();
                     break;
                 case 9:
                     DeleteCourse();
                     break;
                 case 0:
                     return;
             }
         }
     }

     static void AddStudent() throws Exception{
         System.out.print("Nhập tên: ");
         String name = sc.nextLine();
         System.out.print("Nhập email: ");
         String email = sc.nextLine();
         System.out.print("Nhập số điện thoại: ");
         String phone = sc.nextLine();

         Student student = new Student(name, email, phone);
         enrollmentService.addStudent(student);

         System.out.println("Nhập thành công!");
     }

     static void AddCourse() throws Exception {
         System.out.print("Nhập tên: ");
         String name = sc.nextLine();
         System.out.print("Nhập số tín chỉ: ");
         int credits = sc.nextInt();
         sc.nextLine();

         Course course = new Course(name, credits);
         enrollmentService.addCourse(course);

         System.out.println("Nhập thành công!");
     }

     static void EnrollCourse() throws Exception {
         System.out.print("Nhập student id: ");
         int studentId = sc.nextInt();
         sc.nextLine();
         System.out.print("Nhập course id: ");
         int courseId = sc.nextInt();
         sc.nextLine();

         enrollmentService.enrollCourse(studentId, courseId);

         System.out.println("Đăng kí thành công!");
     }

     static void ViewAll() throws Exception {

         List<StudentCourseDTO> studentCourses = enrollmentService.viewAllEnrollments();
         studentCourses.forEach(System.out::println);

     }

     static void ViewStudentToCourses() throws Exception {

         System.out.print("Nhập Student ID: ");
         int studentId = sc.nextInt();
         sc.nextLine();

         List<Course> studentCourses = enrollmentService.viewStudentToCourses(studentId);
         studentCourses.forEach(System.out::println);

     }

     static void ViewCourseToStudents() throws Exception {
         System.out.print("Nhập Course ID: ");
         int courseId = sc.nextInt();
         sc.nextLine();

         List<Student> studentCourses = enrollmentService.viewCourseToStudents(courseId);
         studentCourses.forEach(System.out::println);

     }

     static void Cancel() throws Exception {
         System.out.print("Nhập id muốn hủy đăng kí: ");
         int id = sc.nextInt();
         sc.nextLine();
         enrollmentService.cancelEnrollment(id);
     }

     static void DeleteStudent() throws Exception {
         System.out.print("Nhập student id: ");
         int id = sc.nextInt();
         sc.nextLine();
         enrollmentService.deleteStudent(id);
     }
     static void DeleteCourse() throws Exception {
         System.out.print("Nhập course id: ");
         int id = sc.nextInt();
         sc.nextLine();
         enrollmentService.deleteCourse(id);
     }
}