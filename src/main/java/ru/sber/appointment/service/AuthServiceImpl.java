package ru.sber.appointment.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sber.appointment.jwt_manager.JwtAuthentication;
import ru.sber.appointment.jwt_manager.JwtProvider;
import ru.sber.appointment.jwt_manager.JwtRequest;
import ru.sber.appointment.jwt_manager.JwtResponse;
import ru.sber.appointment.model.User;
import ru.sber.appointment.service.interfaces.AuthService;


import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для авторизации и аутентификации пользователей.
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    @Autowired
    JwtProvider jwtProvider;

    /**
     * Метод для выполнения входа пользователя в систему.
     * @param authRequest объект с данными авторизации пользователя
     * @return ответ с JWT токенами и информацией о пользователе
     */
    @Override
    public ResponseEntity<?> login(JwtRequest authRequest) {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final User user = (User) userService.loadUserByUsername(authRequest.getLogin());
        if (user == null){
            return ResponseEntity.ok(null);
        }
        if ( bCryptPasswordEncoder.matches(authRequest.getPassword(), user.getPassword())){
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getUsername(), refreshToken);
            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken, user));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    /**
     * Метод для получения нового access токена на основе refresh токена.
     * @param refreshToken refresh токен
     * @return объект с новым access токеном
     */
    @Override
    public JwtResponse getAccessToken(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }

        return new JwtResponse(null, null);
    }

    /**
     * Метод для обновления access и refresh токенов.
     * @param refreshToken текущий refresh токен
     * @return объект с новым access и refresh токенами
     * @throws AuthException если токен невалидный
     */
    @Override
    public JwtResponse refresh(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) userService.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("not valid JWT");
    }

    @Override
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
