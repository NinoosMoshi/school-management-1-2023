package com.ninos.dao;

import com.ninos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    User findUserByEmail(String email);


}
