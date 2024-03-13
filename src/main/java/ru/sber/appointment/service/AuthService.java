package ru.sber.appointment.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sber.appointment.configuration.JwtAuthentication;
import ru.sber.appointment.jwt_manager.JwtProvider;
import ru.sber.appointment.jwt_manager.JwtRequest;
import ru.sber.appointment.jwt_manager.JwtResponse;
import ru.sber.appointment.model.User;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    @Autowired
    JwtProvider jwtProvider;


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
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean getAuthoritiesDoctor(String username){
        User user = userService.userRepository.findByUsername(username);
        return user.getAuthorities().contains(roleService.findByName("ROLE_DOCTOR"));
    }

}
