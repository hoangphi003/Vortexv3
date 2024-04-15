package com.techvortex.vortex.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.repository.DoiMatKhauDao;
import com.techvortex.vortex.service.DoiMatKhauService;

@Service
public class DoitMatKhaurImp implements DoiMatKhauService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DoiMatKhauDao userRepository;

    @Override
    public void changeUserPassword(Account user, String password) {
        // Cập nhật mật khẩu mới sau khi đã mã hóa
        // Mã hóa mật khẩu mới
        String encodedPassword = passwordEncoder.encode(password);

        // Cập nhật mật khẩu mới cho người dùng
        user.setPassword(encodedPassword);

        // Lưu thay đổi vào cơ sở dữ liệu
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(Account user, String oldPassword) {
        // Lấy mật khẩu đã mã hóa từ đối tượng người dùng
        String currentPassword = user.getPassword();

        // So sánh mật khẩu cũ với mật khẩu đã mã hóa sử dụng PasswordEncoder
        return passwordEncoder.matches(oldPassword, currentPassword);
    }

    @Override
    public Account getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (username != null) {
            return userRepository.findByUsername(username); // Giả sử bạn có phương thức này trong UserRepository
        }
        return null;
    }
}
