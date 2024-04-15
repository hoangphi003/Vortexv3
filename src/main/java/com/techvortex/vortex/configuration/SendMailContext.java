package com.techvortex.vortex.configuration;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendMailContext {
    @Autowired
    private JavaMailSender emailSender;

    public void sendResetEmail(String to, String resetLink) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Khôi phục Mật khẩu");
            helper.setText("<html><body style='font-family: Arial, sans-serif; color: #333333;'>"
                    + "<p>Chào bạn,<br><br>"
                    + "Bạn đã yêu cầu <strong style='color: #0066cc;'>khôi phục mật khẩu</strong> cho tài khoản của mình. <br>"
                    + "Mật khẩu mới của bạn là: <strong>" + resetLink + "</strong><br><br>"
                    + "Nếu bạn không yêu cầu khôi phục mật khẩu, vui lòng bỏ qua email này. <br><br>"
                    + "Nếu bạn gặp bất kỳ vấn đề gì hoặc cần sự hỗ trợ, vui lòng liên hệ với chúng tôi qua email này. <br><br>"
                    + "Trân trọng,<br>"
                    + "<strong>Đội ngũ Vortex</strong></p></body></html>", true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
