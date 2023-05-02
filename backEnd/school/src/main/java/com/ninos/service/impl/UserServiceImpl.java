package com.ninos.service.impl;

import com.ninos.dao.RoleDao;
import com.ninos.dao.UserDao;
import com.ninos.entity.Role;
import com.ninos.entity.User;
import com.ninos.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User loadUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        String encodePassword = passwordEncoder.encode(password);
        return userDao.save(new User(email, encodePassword));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = loadUserByEmail(email);
        Role role = roleDao.findRoleByName(roleName);
        user.assignRoleToUser(role);
    }
}
