package ru.sber.appointment.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.sber.appointment.jwt_manager.JwtAuthentication;
import ru.sber.appointment.jwt_manager.JwtProvider;
import ru.sber.appointment.jwt_manager.JwtUtils;
import ru.sber.appointment.service.UserServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Класс для фильтрации JWT токена.
 */
@Component
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private final JwtProvider jwtProvider;
    @Autowired
    UserServiceImpl userService;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    /**
     * Метод для фильтрации запросов и проверки JWT токена.
     * @param request запрос
     * @param response ответ
     * @param fc цепочка фильтров
     * @throws IOException если произошла ошибка ввода/вывода
     * @throws ServletException если происходит ошибка связанная с сервлетами
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) request);
        try {
            if (token != null && jwtProvider.validateAccessToken(token)) {
                final Claims claims = jwtProvider.getAccessClaims(token);
                final JwtAuthentication jwtInfoToken = JwtUtils.generate(claims);
                jwtInfoToken.setRoles(userService.findByUsername(jwtInfoToken.getUsername()).getRoles());
                jwtInfoToken.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
            }
        } catch (ExpiredJwtException e) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.getWriter().write("Срок действия JWT токена истек");
            return;
        }
        fc.doFilter(request, response);
    }

    /**
     * Метод для извлечения токена из запроса.
     * @param request HTTP запрос
     * @return токен JWT
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
