package com.portfolio.astrology.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;



@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmailSenderService {

       private final JavaMailSender mailSender;

    public void sendEmail(String to, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("astrology.raffler@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Raffler Astrology Validation Code");
        simpleMailMessage.setText(message);
        this.mailSender.send(simpleMailMessage);
    }
}
