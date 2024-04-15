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
public class sendMailByCancelOrder {
    @Autowired
    private JavaMailSender emailSender;

    public void sendEmailCancelOrder(String email, String username, Date date, String gender, String reason) {

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String dateFormat = simpleDateFormat.format(date);

        MimeMessage message = emailSender.createMimeMessage();

        String reasonMail = "";

        if (reason.equalsIgnoreCase("1")) {
            reasonMail = "Thay đổi chi tiết đơn hàng(Màu sắc, kích thước, số lượng,...).";
        }
        if (reason.equalsIgnoreCase("2")) {
            reasonMail = "Thay đổi địa chỉ nhận hàng.";
        }
        if (reason.equalsIgnoreCase("3")) {
            reasonMail = "Thay đổi số điện thoại.";
        }
        if (reason.equalsIgnoreCase("4")) {
            reasonMail = "Đổi ý, không muốn mua nữa.";
        }
        if (reason.equalsIgnoreCase("5")) {
            reasonMail = "Đặt nhầm sản phẩm.";
        }
        if (reason.equalsIgnoreCase("6")) {
            reasonMail = "Không gọi được cho shop.";
        }
        if (reason.equalsIgnoreCase("7")) {
            reasonMail = "Một số nguyên nhân khác..";
        }

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("Thông báo hủy đơn hàng");
            helper.setText("<html><body style='font-family: Arial, sans-serif; color: #333333;'>"
                    + "<p>Chào " + gender + ", " + username + "<br><br>"
                    + "Bạn đã yêu cầu hủy đơn hàng được đặt ngày <strong style='color: #0066cc;'>" + dateFormat
                    + "</strong> <br><br>"
                    + "Lý do: " + reasonMail + "<br><br>"
                    + "Đơn hàng của bạn đã hủy thành công. <br><br>"
                    + "Chúng tôi hoàn tiền vào tài khoản bạn sau vài phút. <br><br>"
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
