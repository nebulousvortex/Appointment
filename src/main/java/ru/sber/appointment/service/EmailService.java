package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vortexoofnebula@gmail.com");
        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Отправлено");

    }
}