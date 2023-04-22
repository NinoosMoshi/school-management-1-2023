package com.ninos.mapper;

import com.ninos.dto.CourseDTO;
import com.ninos.dto.InstructorDTO;
import com.ninos.entity.Course;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CourseMapper {

    private final InstructorMapper instructorMapper;

    public CourseDTO fromCourse(Course course){
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        courseDTO.setInstructor(instructorMapper.fromInstructor(course.getInstructor()));
        return courseDTO;
    }


    public Course fromCourseDTO(CourseDTO courseDTO){
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        return course;
    }


}
