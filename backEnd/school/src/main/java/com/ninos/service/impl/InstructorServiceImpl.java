package com.ninos.service.impl;

import com.ninos.dao.InstructorDao;
import com.ninos.dto.InstructorDTO;
import com.ninos.entity.Course;
import com.ninos.entity.Instructor;
import com.ninos.entity.User;
import com.ninos.mapper.InstructorMapper;
import com.ninos.service.CourseService;
import com.ninos.service.InstructorService;
import com.ninos.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorDao instructorDao;
    private final InstructorMapper instructorMapper;
    private final UserService userService;
    private final CourseService courseService;


    @Override
    public Page<InstructorDTO> loadAllInstructors(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Instructor> instructorsPage = instructorDao.findAll(pageRequest);
        return new PageImpl<>(instructorsPage.getContent().stream().map(instructor -> instructorMapper.fromInstructor(instructor)).collect(Collectors.toList()),pageRequest, instructorsPage.getTotalElements());
    }

    @Override
    public Instructor loadInstructorById(Long instructorId) {
      return instructorDao.findById(instructorId).orElseThrow(() -> new EntityNotFoundException("Instructor With ID " + instructorId + " Not Found"));
    }

    @Override
    public Page<InstructorDTO> findInstructorsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Instructor> instructorPage = instructorDao.findInstructorsByName(name, pageRequest);
        return new PageImpl<>(instructorPage.getContent().stream().map(instructor ->
                instructorMapper.fromInstructor(instructor))
                .collect(Collectors.toList()), pageRequest, instructorPage.getTotalElements());
    }

    @Override
    public InstructorDTO loadInstructorByEmail(String email) {
        Instructor instructor = instructorDao.findInstructorByEmail(email);
        return instructorMapper.fromInstructor(instructor);
    }

    @Override
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
        User user = userService.createUser(instructorDTO.getUser().getEmail(), instructorDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(), "Instructor");
        Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(user);
        Instructor savedInstructor = instructorDao.save(instructor);
        return instructorMapper.fromInstructor(savedInstructor);
    }

    @Override
    public InstructorDTO updateInstructor(InstructorDTO instructorDTO) {
        Instructor loadedInstructor = loadInstructorById(instructorDTO.getInstructorId());
        Instructor instructor = instructorMapper.fromInstructorDTO(instructorDTO);
        instructor.setUser(loadedInstructor.getUser());
        instructor.setCourses(loadedInstructor.getCourses());
        Instructor updatedInstructor = instructorDao.save(instructor);
        InstructorDTO dto = instructorMapper.fromInstructor(updatedInstructor);
        return dto;
    }

    @Override
    public List<InstructorDTO> fetchInstructors() {
        List<InstructorDTO> collectDto = instructorDao.findAll().stream().map(instructor ->
                instructorMapper.fromInstructor(instructor)).collect(Collectors.toList());
        return collectDto;
    }


    @Override
    public void removeInstructor(Long instructorId) {
        Instructor instructor = loadInstructorById(instructorId);
        for (Course course: instructor.getCourses()){
            courseService.removeCourse(course.getCourseId());
        }
        instructorDao.deleteById(instructorId);
    }
}
