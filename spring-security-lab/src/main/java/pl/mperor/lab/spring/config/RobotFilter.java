package pl.mperor.lab.spring.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class RobotFilter extends OncePerRequestFilter {

    public static final String HEADER_NAME = "x-robot-password";

    private final AuthenticationManager authenticationManager;

    public RobotFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 0. Should execute filter?
        if (!Collections.list(request.getHeaderNames()).contains(HEADER_NAME)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 1. Authentication Decision
        String password = request.getHeader(HEADER_NAME);
        var unauthenticated = RobotAuthentication.unauthenticated(password);

        try {
            // OK 👍
            var authenticate = authenticationManager.authenticate(unauthenticated);
            var newContext = SecurityContextHolder.createEmptyContext();
            newContext.setAuthentication(authenticate);
            SecurityContextHolder.setContext(newContext);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException e) {
            // WRONG 👎
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/plain;charset=utf-8");
            response.getWriter().println(e.getMessage());
        }

        // 2. Do the Rest™️
    }


}
