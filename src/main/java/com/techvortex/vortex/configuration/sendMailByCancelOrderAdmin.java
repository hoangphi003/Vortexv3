package com.techvortex.vortex.configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class sendMailByCancelOrderAdmin {
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmailCancelOrder(String email, String username, Date date, String gender, String reason) {

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String dateFormat = simpleDateFormat.format(date);

        MimeMessage message = emailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Đơn hàng đã bị hủy");
            helper.setText("<html><body style='font-family: Arial, sans-serif; color: #333333;'>"
                    + "<p>Chào " + gender + ", " + username + "<br><br>"
                    + "Chúng tôi đã yêu cầu hủy đơn hàng được đặt ngày <strong style='color: #0066cc;'>" + dateFormat
                    + "</strong> <br><br>"
                    + "Lý do: " + reason + "<br><br>"
                    + "Đơn hàng của bạn đã bị hủy. <br><br>"
                    + "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua email này"
                    + "Hotline: <b>0706350313</b> (gặp anh Phi) <br><br>"
                    + "Trân trọng,<br>"
                    + "<strong>Đội ngũ Vortex</strong><br>"
                    + "*Quý khách vui lòng không trả lời email này* </p></body></html>", true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
