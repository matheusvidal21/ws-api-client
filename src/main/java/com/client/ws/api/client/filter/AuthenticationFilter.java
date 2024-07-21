package com.client.ws.api.client.filter;

import com.client.ws.api.client.exception.NotFoundException;
import com.client.ws.api.client.repository.jpa.UserDetailsRepository;
import com.client.ws.api.client.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserDetailsRepository userDetailsRepository;

    public AuthenticationFilter(JwtTokenService jwtTokenService, UserDetailsRepository userDetailsRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = getBearerToken(request);
        if (Boolean.TRUE.equals(jwtTokenService.validateToken(token))) {
            authByToken(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authByToken(String token) {
        Long userId = jwtTokenService.getUserId(token);
        var userOpt = userDetailsRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userOpt, null, userOpt.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private String getBearerToken(HttpServletRequest request) {
        final String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || token.isBlank() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }
}
