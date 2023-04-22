package com.ninos.mapper;

import com.ninos.dto.InstructorDTO;
import com.ninos.entity.Instructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class InstructorMapper {

    public InstructorDTO fromInstructor(Instructor instructor){
        InstructorDTO instructorDTO = new InstructorDTO();
        BeanUtils.copyProperties(instructor, instructorDTO);   // copy from source to target
        return instructorDTO;
    }


    public Instructor fromInstructorDTO(InstructorDTO instructorDTO){
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDTO, instructor);   // copy from source to target
        return instructor;
    }

}
