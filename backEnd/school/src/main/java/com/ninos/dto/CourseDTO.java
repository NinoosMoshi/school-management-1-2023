package com.ninos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {

    private Long courseId;
    private String courseName;
    private String courseDuration;
    private String courseDescription;
    private InstructorDTO instructor;


}
