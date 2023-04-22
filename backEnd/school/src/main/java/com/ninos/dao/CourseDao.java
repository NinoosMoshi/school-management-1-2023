package com.ninos.dao;

import com.ninos.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CourseDao extends JpaRepository<Course,Long> {

//    List<Course> findCoursesByCourseNameContains(String keyword);
    Page<Course> findCoursesByCourseNameContains(String keyword, Pageable pageable);



//    @Query(value = "select * from courses as c where c.course_id in (select e.course_id from enrolled_in as e where e.student_id=:studentId)",nativeQuery = true)
//    List<Course> getCoursesByStudentId(@Param("studentId") Long studentId);
    @Query(value = "select * from courses as c where c.course_id in (select e.course_id from enrolled_in as e where e.student_id=:studentId)",nativeQuery = true)
    Page<Course> getCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);


    @Query(value = "select * from courses as c where c.course_id not in (select e.course_id from enrolled_in as e where e.student_id=:studentId)",nativeQuery = true)
    Page<Course> getNonEnrolledInCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);

    @Query("select c from Course as c where c.instructor.instructorId=:instructorId")
    Page<Course> getCoursesByInstructorId(@Param("instructorId") Long instructorId, Pageable pageable);

}
