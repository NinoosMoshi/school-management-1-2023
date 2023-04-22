package com.ninos.service;

import com.ninos.dto.CourseDTO;
import com.ninos.dto.InstructorDTO;
import com.ninos.entity.Instructor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstructorService {

    Page<InstructorDTO> loadAllInstructors(int page, int size);

    Instructor loadInstructorById(Long instructorId);

    Page<InstructorDTO> findInstructorsByName(String name, int page, int size);

    InstructorDTO loadInstructorByEmail(String email);

    InstructorDTO createInstructor(InstructorDTO instructorDTO);

    InstructorDTO updateInstructor(InstructorDTO instructorDTO);

    List<InstructorDTO> fetchInstructors();

    void removeInstructor(Long instructorId);

}
