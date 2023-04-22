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
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id",nullable = false)
    private Long roleId;

    @Basic
    @Column(nullable = false, length = 45, unique = true)
    private String name;


    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY) // lazy because we don't want to get users when we get role
    private Set<User> users = new HashSet<>();



    public Role(String name) {
        this.name = name;
    }
}
