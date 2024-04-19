package com.techvortex.vortex.serviceimplement;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.entity.Authority;
import com.techvortex.vortex.entity.Role;
import com.techvortex.vortex.repository.AccountDao;
import com.techvortex.vortex.repository.AuthorityDao;
import com.techvortex.vortex.repository.RoleDao;
import com.techvortex.vortex.service.AuthorityService;

@Service
public class AuthorityServiceImp implements AuthorityService{
    @Autowired
    AuthorityDao authorityRepository;

    @Autowired
    AccountDao accountRepository;

    @Autowired
    RoleDao roleRepository;

    @Override
    public List<Authority> getAllAuthorities() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority saveAuthority(Authority authority) {
        // Kiểm tra xem Role đã tồn tại trong cơ sở dữ liệu chưa
        Role existingRole = roleRepository.findById(authority.getRole().getRoleId()).orElse(null);

        if (existingRole != null) {
            // Nếu Role đã tồn tại, gán Role cho Authority
            authority.setRole(existingRole);
        } else {
            // Nếu Role chưa tồn tại, có thể xử lý tùy thuộc vào yêu cầu của bạn
            // Ví dụ: ném một exception hoặc thông báo lỗi
            throw new RuntimeException("Role with RoleId " + authority.getRole().getRoleId() + " does not exist.");
        }

        // Lưu Authority
        return authorityRepository.save(authority);
    }


    @Override
    public void deleteAuthority(Integer id) {
        authorityRepository.deleteByAuthorityID(id);
    }

    @Override
    public List<Authority> findAuthoritiesOdAdministrators() {
        try {
            List<Account> accounts = accountRepository.getAdministrators();

            // Kiểm tra nếu accounts là null hoặc rỗng
            if (accounts == null || accounts.isEmpty()) {
                System.out.println("Không tìm thấy tài khoản quản trị.");
                return Collections.emptyList(); // hoặc null tuỳ vào cách bạn muốn xử lý
            } else {
                System.out.println("tìm thấy tài khoản quản trị.");
            }

            List<Authority> authorities = authorityRepository.authoritiesOf(accounts);

            // Kiểm tra nếu authorities là null
            if (authorities == null) {
                System.out.println("Danh sách quyền của quản trị viên không có sẵn.");
                return Collections.emptyList(); // hoặc null tuỳ vào cách bạn muốn xử lý
            } else {
                System.out.println("Danh sách quyền của quản trị viên có sẵn.");
                return authorities;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // hoặc null tuỳ vào cách bạn muốn xử lý
        }
    }
}
