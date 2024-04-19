package com.techvortex.vortex.service;

import java.util.List;

import com.techvortex.vortex.entity.Role;

public interface RoleService {

    void save(Role role);

    List<Role> findAll();

    public Role findById(String RoleId);

    public void delete(Role role);

    void deleteById(String roleId);

}
