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
import ru.sber.appointment.service.interfaces.EmailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender mailSender;

    @Override
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

    @Override
    @Async
    public void sendEmailWithQR(String to, String subject, String text) {
        String qrCodeUrl = "http://api.qrserver.com/v1/create-qr-code/?data=" + URLEncoder.encode(text, StandardCharsets.UTF_8) + "&size=200x200";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
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
                helper.setSubject(subject);
                helper.setText(text, true);
                helper.addAttachment("QRCode.png", new ByteArrayResource(qrCode));
                mailSender.send(message);
                System.out.println("Отправлено");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Не удалось загрузить QR-код");
        }
    }
}