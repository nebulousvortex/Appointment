package ru.sber.appointment.service.interfaces;

import org.springframework.http.ResponseEntity;
import ru.sber.appointment.jwt_manager.JwtAuthentication;
import ru.sber.appointment.jwt_manager.JwtRequest;
import ru.sber.appointment.jwt_manager.JwtResponse;

import javax.security.auth.message.AuthException;

public interface AuthService {
    ResponseEntity<?> login(JwtRequest authRequest);

    JwtResponse getAccessToken(String refreshToken);

    JwtResponse refresh(String refreshToken) throws AuthException;

    JwtAuthentication getAuthInfo();

    boolean getAuthoritiesDoctor(String username);

    boolean getAuthoritiesAdmin(String username);
}
