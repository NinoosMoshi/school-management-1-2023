package com.ninos.dao;

import com.ninos.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {

    Role findRoleByName(String name);

}
