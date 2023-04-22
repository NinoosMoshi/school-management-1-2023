package com.ninos.service.impl;

import com.ninos.dao.RoleDao;
import com.ninos.entity.Role;
import com.ninos.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Override
    public Role createRole(String roleName) {
        return roleDao.save(new Role(roleName));
    }
}
