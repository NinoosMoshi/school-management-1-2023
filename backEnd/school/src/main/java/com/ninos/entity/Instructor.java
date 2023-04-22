package com.ninos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "instructors")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id",nullable = false)
    private Long instructorId;

    @Basic
    @Column(nullable = false, length = 45)
    private String firstName;

    @Basic
    @Column(nullable = false, length = 45)
    private String lastName;

    @Basic
    @Column(nullable = false, length = 64)
    private String summary;

    @OneToMany(mappedBy = "instructor",fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    @OneToOne(cascade = CascadeType.REMOVE)  // when we remove instructor it will remove user automatically
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    public Instructor(String firstName, String lastName, String summary, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.summary = summary;
        this.user = user;
    }
}
