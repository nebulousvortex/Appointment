package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sber.appointment.exception.QRGenerationException;
import ru.sber.appointment.jwt_manager.JwtProvider;
import ru.sber.appointment.model.RestResponse;
import ru.sber.appointment.service.interfaces.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Сервис для отправки электронных писем.
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    RestResponse restResponse;

    String sender;
    String qrGeneratorUrl;
    String confirmUrl;

    public EmailServiceImpl(@Value("${spring.mail.username}") String sender,
                            @Value("${mail.qr}") String qrGeneratorUrl,
                            @Value("${mail.confirm}") String confirmUrl) {
        this.sender = sender;
        this.qrGeneratorUrl = qrGeneratorUrl;
        this.confirmUrl = confirmUrl;
    }

    /**
     * Метод для отправки простого текстового электронного письма.
     * @param to адрес получателя
     * @param subject тема письма
     * @param text текст письма
     */
    @Override
    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);

        mailSender.send(message);
    }

    /**
     * Метод для отправки электронного письма с прикрепленным QR-кодом.
     * @param to адрес получателя
     * @param username имя пользователя
     * @param id идентификатор записи
     */
    @Override
    @Async
    public void sendEmailWithQR(String to, String username, Long id) {
        String confirmToken = jwtProvider.generateConfirmToken(username);
        String url = confirmUrl + confirmToken + "/" + username + "/" + id;
        String qrCodeUrl = qrGeneratorUrl + url + "&size=400x400";

        ResponseEntity<?> response = restResponse.getResponseEntity(qrCodeUrl, HttpMethod.GET, byte[].class);

        byte[] qrCode = (byte[])response.getBody();
        if (qrCode != null) {
            MimeMessage message = mailSender.createMimeMessage();
            try {
                buildMail(to, message, qrCode);
            } catch (MessagingException e) {
                throw new QRGenerationException("Ошибка в отправлении QR кода");
            }
        }
    }

    private void buildMail(String to, MimeMessage message, byte[] qrCode) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject("Подтверждение");
        helper.setText("Подтвердите запись на прием.");
        helper.addAttachment("QRCode.png", new ByteArrayResource(qrCode));
        mailSender.send(message);
    }

}