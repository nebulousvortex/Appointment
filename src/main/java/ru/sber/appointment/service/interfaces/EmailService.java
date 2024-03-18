package ru.sber.appointment.service.interfaces;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    @Async
    void sendEmail(String to, String subject, String text);

    @Async
    void sendEmailWithQR(String to, String username, Long id);
}
