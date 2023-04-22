package com.ninos.service.impl;

import com.ninos.dao.StudentDao;
import com.ninos.dto.StudentDTO;
import com.ninos.entity.Course;
import com.ninos.entity.Student;
import com.ninos.entity.User;
import com.ninos.mapper.StudentMapper;
import com.ninos.service.CourseService;
import com.ninos.service.StudentService;
import com.ninos.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final StudentMapper studentMapper;
    private final UserService userService;
    private final CourseService courseService;




    @Override
    public Student loadStudentById(Long studentId) {
        return studentDao.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student With ID " + studentId + " Not Found"));
    }

    @Override
    public Page<StudentDTO> loadStudentsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Student> studentPage = studentDao.findStudentsByName(name, pageRequest);
        return new PageImpl<>(studentPage.getContent().stream().map(student ->
                studentMapper.fromStudent(student))
                .collect(Collectors.toList()), pageRequest, studentPage.getTotalElements());
    }

    @Override
    public StudentDTO loadStudentByEmail(String email) {
        Student student = studentDao.findStudentByEmail(email);
        StudentDTO studentDTO = studentMapper.fromStudent(student);
        return studentDTO;
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        User user = userService.createUser(studentDTO.getUser().getEmail(), studentDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(), "Student");
        Student student = studentMapper.fromStudentDTO(studentDTO);
        student.setUser(user);
        Student savedStudent = studentDao.save(student);
        return studentMapper.fromStudent(savedStudent);
    }

    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        Student loadedStudent = loadStudentById(studentDTO.getStudentId());
        Student student = studentMapper.fromStudentDTO(studentDTO);
        student.setUser(loadedStudent.getUser());
        student.setCourses(loadedStudent.getCourses());
        Student updatedStudent = studentDao.save(student);
        return studentMapper.fromStudent(updatedStudent);
    }

    @Override
    public void removeStudent(Long studentId) {
        Student student = loadStudentById(studentId);
        Iterator<Course> courseIterator = student.getCourses().iterator();
        if (courseIterator.hasNext()){
            Course course = courseIterator.next();
            course.removeStudentFromCourse(student);
        }
        studentDao.deleteById(studentId);
    }
}
