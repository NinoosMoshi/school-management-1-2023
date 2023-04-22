package com.ninos.controller;

import com.ninos.dto.CourseDTO;
import com.ninos.entity.Course;
import com.ninos.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/courses")
public class CourseRestController {


    private final CourseService courseService;


    @GetMapping("/all-courses")
    public Page<CourseDTO> getAllCourses(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size){
        return courseService.loadAllCourses(page, size);
    }



    @GetMapping("/search")
    public Page<CourseDTO> searchCourses(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size){
       return courseService.findCoursesByCourseName(keyword, page, size);
    }


    @DeleteMapping("/delete/{courseId}")
    public void deleteCourse(@PathVariable Long courseId){
        courseService.removeCourse(courseId);
    }


    @PostMapping("/create")
    public CourseDTO saveCourse(@RequestBody CourseDTO courseDTO){
       return courseService.createCourse(courseDTO);
    }

    @PutMapping("/update/{courseId}")
    public CourseDTO updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long courseId){
       courseDTO.setCourseId(courseId);
       return courseService.updateCourse(courseDTO);
    }


    @PostMapping("/{courseId}/enroll/students/{studentId}")
    public void enrollStudentInCourse(@PathVariable Long courseId, @PathVariable Long studentId){
       courseService.assignStudentToCourse(courseId,studentId);
    }




}
