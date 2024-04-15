package com.techvortex.vortex.restcontroller;

import java.security.SecureRandom;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.techvortex.vortex.configuration.SendMailContext;
import com.techvortex.vortex.entity.Account;
import com.techvortex.vortex.service.ForgotPasswordService;

@RestController
public class ForgotRestController {

    @Autowired
    private ForgotPasswordService forgotService;

    @Autowired
    private SendMailContext emailService;

    @PostMapping("/forgotpassword")
    public ResponseEntity<Account> lostPassword(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");

        Account user = forgotService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // Tạo một mật khẩu ngẫu nhiên
        String newPassword = generateRandomPassword();

        // Cập nhật mật khẩu trong cơ sở dữ liệu
        forgotService.updatePasswordByEmail(email, newPassword);

        // Gửi email đặt lại mật khẩu với mật khẩu mới
        emailService.sendResetEmail(email, newPassword);

        return ResponseEntity.ok(user);
    }

    private String generateRandomPassword() {
        // Chuỗi chứa ký tự cho mật khẩu
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Độ dài của mật khẩu
        int length = 10;

        // Sử dụng SecureRandom để tạo số ngẫu nhiên
        SecureRandom random = new SecureRandom();

        // StringBuilder để xây dựng mật khẩu ngẫu nhiên
        StringBuilder password = new StringBuilder(length);

        // Tạo mật khẩu ngẫu nhiên
        for (int i = 0; i < length; i++) {
            // Lấy một ký tự ngẫu nhiên từ chuỗi ký tự
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);

            // Thêm ký tự này vào mật khẩu
            password.append(randomChar);
        }

        // Trả về mật khẩu dưới dạng chuỗi
        return password.toString();
    }

}
