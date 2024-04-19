package com.techvortex.vortex.service;

import java.util.List;

import com.techvortex.vortex.entity.Authority;

public interface AuthorityService {
    List<Authority> getAllAuthorities();

    Authority saveAuthority(Authority authority);

    void deleteAuthority(Integer id);

    public List<Authority> findAuthoritiesOdAdministrators();
}
