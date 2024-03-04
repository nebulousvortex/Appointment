package ru.sber.appointment.jwt_manager;

import io.jsonwebtoken.Claims;
import ru.sber.appointment.configuration.JwtAuthentication;

public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

}
