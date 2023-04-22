package com.ninos.controller;

import com.ninos.dto.CourseDTO;
import com.ninos.dto.InstructorDTO;
import com.ninos.entity.User;
import com.ninos.service.CourseService;
import com.ninos.service.InstructorService;
import com.ninos.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/instructors")
public class InstructorRestController {

    private final InstructorService instructorService;
    private final CourseService courseService;
    private final UserService userService;


    @GetMapping("/all-instructors")
    public Page<InstructorDTO> getAllInstructorsPagination(@RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "5") int size){
        return instructorService.loadAllInstructors(page, size);
    }


    @GetMapping("/search")
    public Page<InstructorDTO> searchInstructors(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "5") int size
                                                 ){
        return instructorService.findInstructorsByName(keyword, page, size);
    }


    @GetMapping("/all")
    public List<InstructorDTO> findAllInstructors(){
        return instructorService.fetchInstructors();
    }


    @DeleteMapping("/{instructorId}")
    public void deleteInstructor(@PathVariable Long instructorId){
        instructorService.removeInstructor(instructorId);
    }


    @PostMapping("/create")
    public InstructorDTO saveInstructor(@RequestBody InstructorDTO instructorDTO){
        User user = userService.loadUserByEmail(instructorDTO.getUser().getEmail());
        if (user != null) throw new RuntimeException("Email Already Exist");
        return instructorService.createInstructor(instructorDTO);
    }


    @PutMapping("/{instructorId}")
    public InstructorDTO updateInstructor(@RequestBody InstructorDTO instructorDTO, @PathVariable Long instructorId){
        instructorDTO.setInstructorId(instructorId);
        return instructorService.updateInstructor(instructorDTO);
    }


    @GetMapping("/{instructorId}/courses")
    public Page<CourseDTO> coursesByInstructorId(@PathVariable Long instructorId,
                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "5") int size){
       return courseService.fetchCoursesForInstructor(instructorId, page, size);
    }


    @GetMapping("/find")
    public InstructorDTO loadInstructorByEmail(@RequestParam(name = "email", defaultValue = "") String email){
       return instructorService.loadInstructorByEmail(email);
    }


}
