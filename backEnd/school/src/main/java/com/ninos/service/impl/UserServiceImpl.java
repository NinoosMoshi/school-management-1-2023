package com.ninos.service.impl;

import com.ninos.dao.RoleDao;
import com.ninos.dao.UserDao;
import com.ninos.entity.Role;
import com.ninos.entity.User;
import com.ninos.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;


    @Override
    public User loadUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        return userDao.save(new User(email, password));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = loadUserByEmail(email);
        Role role = roleDao.findRoleByName(roleName);
        user.assignRoleToUser(role);
    }
}
