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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Basic
    @Column(nullable = false, length = 45, unique = true)
    private String email;

    @Basic
    @Column(nullable = false, length = 64)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER) // when we fetch user we will fetch role as well
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Student student;

    @OneToOne(mappedBy = "user")
    private Instructor instructor;







    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void assignRoleToUser(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRoleFromUser(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

}
