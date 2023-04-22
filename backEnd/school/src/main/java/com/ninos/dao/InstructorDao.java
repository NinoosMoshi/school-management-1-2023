package com.ninos.dao;

import com.ninos.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorDao extends JpaRepository<Instructor, Long> {

//    @Query("select i from Instructor as i where i.firstName like %:name% or i.lastName like %:name%")
//    List<Instructor> findInstructorsByName(@Param("name") String name);
      @Query("select i from Instructor as i where i.firstName like %:name% or i.lastName like %:name%")
      Page<Instructor> findInstructorsByName(@Param("name") String name, PageRequest pageRequest);




    @Query("select i from Instructor as i where i.user.email=:email")
    Instructor findInstructorByEmail(@Param("email") String email);

}
