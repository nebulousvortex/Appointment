package ru.sber.appointment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sber.appointment.jwt_manager.JwtProvider;
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
        message.setFrom("vortexoofnebula@gmail.com");
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
        String url = "http://localhost:8080/api/v1/ticket/put/tickets/confirmed/"+confirmToken+"/"+username+"/"+id;
        System.out.println(url);
        String qrCodeUrl = "http://api.qrserver.com/v1/create-qr-code/?data=" + url + "&size=400x400";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_OCTET_STREAM));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(qrCodeUrl, HttpMethod.GET, entity, byte[].class);
        byte[] qrCode = response.getBody();
        if (qrCode != null) {
            MimeMessage message = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom("vortexoofnebula@gmail.com");
                helper.setTo(to);
                helper.setSubject("Подтверждение");
                helper.setText("Подтвердите запись на прием.");
                helper.addAttachment("QRCode.png", new ByteArrayResource(qrCode));
                mailSender.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Не удалось загрузить QR-код");
        }
    }
}