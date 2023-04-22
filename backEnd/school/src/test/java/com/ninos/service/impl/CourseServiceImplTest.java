package com.ninos.service.impl;

import com.ninos.dao.CourseDao;
import com.ninos.dao.InstructorDao;
import com.ninos.dao.StudentDao;
import com.ninos.dto.CourseDTO;
import com.ninos.entity.Course;
import com.ninos.entity.Instructor;
import com.ninos.mapper.CourseMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseDao courseDao;

    @Mock
    private InstructorDao instructorDao;

    @Mock
    private CourseMapper courseMapper;


    @InjectMocks
    private CourseServiceImpl courseService;


    @Test
    void testLoadCourseById() {
        Course course = new Course();
        course.setCourseId(1L);

        when(courseDao.findById(any())).thenReturn(Optional.of(course));

        Course actualCourse = courseService.loadCourseById(1L);

        assertEquals(course, actualCourse);
    }

    @Test
    void testExceptionForNotFoundCourseById(){
       assertThrows(EntityNotFoundException.class, () -> courseService.loadCourseById(2L));
    }



    @Test
    public void testCreateCourse() {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setCourseName("Java");
        courseDTO.setCourseDescription("Learn Java Programming");

        Course course = new Course();
        course.setCourseName("Java");
        course.setCourseDescription("Learn Java Programming");

        Instructor instructor = new Instructor();
        instructor.setInstructorId(1L);

        when(instructorDao.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseMapper.fromCourseDTO(courseDTO)).thenReturn(course);
        when(courseMapper.fromCourse(course)).thenReturn(courseDTO);
        when(courseDao.save(course)).thenReturn(course);

        CourseDTO savedCourse = courseService.createCourse(courseDTO);

        assertEquals(courseDTO.getCourseName(), savedCourse.getCourseName());
        assertEquals(courseDTO.getCourseDescription(), savedCourse.getCourseDescription());
    }







    @Test
    void updateCourse() {
    }

    @Test
    void findCoursesByCourseName() {
    }

    @Test
    void assignStudentToCourse() {
    }

    @Test
    void fetchCoursesForStudent() {
    }

    @Test
    void fetchNonEnrolledInCoursesForStudent() {
    }

    @Test
    void removeCourse() {
    }

    @Test
    void fetchCoursesForInstructor() {
    }
}