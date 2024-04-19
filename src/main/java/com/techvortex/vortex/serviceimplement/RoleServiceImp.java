package com.techvortex.vortex.serviceimplement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import com.techvortex.vortex.entity.Role;
import com.techvortex.vortex.repository.RoleDao;
import com.techvortex.vortex.service.RoleService;

@Service
public class RoleServiceImp implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    @Transactional
    public Role findById(String RoleId) {
        return roleDao.findById(RoleId).get();
    }

    @Override
    public void delete(Role role) {
        roleDao.delete(role);
    }

    @Override
    @Transactional
    public void deleteById(String roleId) {
        Optional<Role> roleOptional = roleDao.findById(roleId);
        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            roleDao.delete(role);
        } else {
            throw new IllegalArgumentException("Role không tồn tại!");
        }
    }
}
