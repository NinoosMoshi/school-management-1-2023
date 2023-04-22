package com.ninos.service.impl;

import com.ninos.dao.CourseDao;
import com.ninos.dao.InstructorDao;
import com.ninos.dao.StudentDao;
import com.ninos.dto.CourseDTO;
import com.ninos.entity.Course;
import com.ninos.entity.Instructor;
import com.ninos.entity.Student;
import com.ninos.mapper.CourseMapper;
import com.ninos.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final InstructorDao instructorDao;
    private final CourseMapper courseMapper;


    @Override
    public Page<CourseDTO> loadAllCourses(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> coursesPage = courseDao.findAll(pageRequest);
        return new PageImpl<>(coursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest, coursesPage.getTotalElements());
    }

    @Override
    public Course loadCourseById(Long courseId) {
        return courseDao.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course With ID " + courseId + " Not Found"));
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = courseMapper.fromCourseDTO(courseDTO);
        Instructor instructor = instructorDao.findById(courseDTO.getInstructor().getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor With ID " + courseDTO.getInstructor().getInstructorId() + " Not Found"));
        course.setInstructor(instructor);
        Course savedCourse = courseDao.save(course);
        return courseMapper.fromCourse(savedCourse);
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course loadCourse = loadCourseById(courseDTO.getCourseId());
        Instructor instructor = instructorDao.findById(courseDTO.getInstructor().getInstructorId()).orElseThrow(() -> new EntityNotFoundException("Instructor With ID " + courseDTO.getInstructor().getInstructorId() + " Not Found"));
        Course course = courseMapper.fromCourseDTO(courseDTO);
        course.setInstructor(instructor);
        course.setStudents(loadCourse.getStudents());
        Course updatedCourse = courseDao.save(course);
        return courseMapper.fromCourse(updatedCourse);
    }

    @Override
    public Page<CourseDTO> findCoursesByCourseName(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> coursesPage = courseDao.findCoursesByCourseNameContains(keyword,pageRequest);
        return new PageImpl<>(coursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()),pageRequest, coursesPage.getTotalElements());
    }

    @Override
    public void assignStudentToCourse(Long courseId, Long studentId) {
        Student student = studentDao.findById(studentId).orElseThrow(() -> new EntityNotFoundException("Student With ID " + studentId + " Not Found"));
        Course course = loadCourseById(courseId);
        course.assignStudentToCourse(student);
    }

    @Override
    public Page<CourseDTO> fetchCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> studentCoursesPage = courseDao.getCoursesByStudentId(studentId, pageRequest);
        return new PageImpl<>(studentCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, studentCoursesPage.getTotalElements());

    }

    @Override
    public Page<CourseDTO> fetchNonEnrolledInCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> nonEnrolledInCoursesPage = courseDao.getNonEnrolledInCoursesByStudentId(studentId,pageRequest);
        return new PageImpl<>(nonEnrolledInCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, nonEnrolledInCoursesPage.getTotalElements());

    }

    @Override
    public void removeCourse(Long courseId) {
      courseDao.deleteById(courseId);
    }

    @Override
    public Page<CourseDTO> fetchCoursesForInstructor(Long instructorId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> instructorCoursesPage = courseDao.getCoursesByInstructorId(instructorId,pageRequest);
        return new PageImpl<>(instructorCoursesPage.getContent().stream().map(course -> courseMapper.fromCourse(course)).collect(Collectors.toList()), pageRequest, instructorCoursesPage.getTotalElements());

    }
}
