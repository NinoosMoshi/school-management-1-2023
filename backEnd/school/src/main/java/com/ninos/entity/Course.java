package com.ninos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id",nullable = false)
    private Long courseId;

    @Basic
    @Column(nullable = false, length = 45)
    private String courseName;

    @Basic
    @Column(nullable = false, length = 45)
    private String courseDuration;

    @Basic
    @Column(nullable = false, length = 64)
    private String courseDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id",referencedColumnName = "instructor_id",nullable = false)
    private Instructor instructor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "enrolled_in",
    joinColumns = {@JoinColumn(name = "course_id")},
    inverseJoinColumns = {@JoinColumn(name = "student_id")}
    )
    private Set<Student> students = new HashSet<>();


    public void assignStudentToCourse(Student student){
        this.students.add(student);
        student.getCourses().add(this);
    }

    public void removeStudentFromCourse(Student student){
        this.students.remove(student);
        student.getCourses().remove(this);
    }











}
