package com.epicode.capstone.email;

import com.epicode.capstone.security.User;
import com.epicode.capstone.travel.Travel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendMail(User user) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to");
            String emailContent =
                    "<head>" +
                            "<style>"
                            + "body { font-family: 'Tenor Sans', Arial, sans-serif; font-weight: 400; }"
                            + ".email-container { padding: 20px; }"
                            + "</style>"
                            + "</head>"
                            + "<body>"
                            + "<div class='email-container'>"
                            + "<div>"
                            + "<p style='font-size: 18px;'>Hello <span style='color: blue;'><strong>"
                            + user.getFirstName() + " " + user.getLastName()
                            + "</div>"
                            + "</div>"
                            + "</body>"
                            + "</html>";

            helper.setText(emailContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendConfirmMail(User user, Travel travel) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to");
            String emailContent =
                    "<head>" +
                            "<style>"
                            + "body { font-family: 'Tenor Sans', Arial, sans-serif; font-weight: 400; }"
                            + ".email-container { padding: 20px; }"
                            + "</style>"
                            + "</head>"
                            + "<body>"
                            + "<div class='email-container'>"
                            + "<div>"
                            + "<p style='font-size: 18px;'>Hello <span style='color: blue;'><strong>"
                            + user.getFirstName() + " " + user.getLastName()
                            + "</strong></span>"
                            + "<p style='font-size: 18px;'>"
                            + travel.getName() + "  " + travel.getPrice()
                            + "</div>"
                            + "</div>"
                            + "</body>"
                            + "</html>";

            helper.setText(emailContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
