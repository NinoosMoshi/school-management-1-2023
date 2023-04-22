package com.ninos.runner;

import com.ninos.dao.UserDao;
import com.ninos.dto.CourseDTO;
import com.ninos.dto.InstructorDTO;
import com.ninos.dto.StudentDTO;
import com.ninos.dto.UserDTO;
import com.ninos.entity.Student;
import com.ninos.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@AllArgsConstructor
@Component
public class MyRunner  { //implements CommandLineRunner

//    private final RoleService roleService;
//    private final UserService userService;
//    private final InstructorService instructorService;
//    private final CourseService courseService;
//    private final StudentService studentService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        createRole();
//        createAdmin();
//        createInstructors();
//        createCourses();
//        StudentDTO student = createStudent();
//        assignCourseToStudent(student);
//    }
//
//
//
//    private void createRole() {
//        Arrays.asList("Admin","Instructor","Student").forEach(role -> roleService.createRole(role));
//    }
//
//    private void createAdmin() {
//        userService.createUser("ninos@gmail.com", "111");
//        userService.assignRoleToUser("ninos@gmail.com", "Admin");
//    }
//
//    private void createInstructors() {
//        for (int i=0; i < 10; i++){
//            InstructorDTO instructorDTO = new InstructorDTO();
//            instructorDTO.setFirstName("instructor"+i+" FN");
//            instructorDTO.setLastName("instructor"+i+" LN");
//            instructorDTO.setSummary("master"+i);
//            UserDTO userDTO = new UserDTO();
//            userDTO.setEmail("instructor"+i+"@gmail.com");
//            userDTO.setPassword("1234");
//            instructorDTO.setUser(userDTO);
//            instructorService.createInstructor(instructorDTO);
//        }
//    }
//
//    private void createCourses() {
//        for(int i=0; i<20; i++){
//            CourseDTO courseDTO = new CourseDTO();
//            courseDTO.setCourseDescription("Java"+i);
//            courseDTO.setCourseDuration(i+"Hours");
//            courseDTO.setCourseName("java course"+i);
//            InstructorDTO instructorDTO = new InstructorDTO();
//            instructorDTO.setInstructorId(1L);
//            courseDTO.setInstructor(instructorDTO);
//            courseService.createCourse(courseDTO);
//        }
//    }
//
//
//    private StudentDTO createStudent() {
//        StudentDTO studentDTO = new StudentDTO();
//        studentDTO.setFirstName("studentFN");
//        studentDTO.setLastName("studentLN");
//        studentDTO.setLevel("intermediate");
//        UserDTO userDTO = new UserDTO();
//        userDTO.setEmail("student@gmail.com");
//        userDTO.setPassword("1234");
//        studentDTO.setUser(userDTO);
//        return studentService.createStudent(studentDTO);
//    }
//
//    private void assignCourseToStudent(StudentDTO student) {
//        courseService.assignStudentToCourse(1L, student.getStudentId());
//    }




}
