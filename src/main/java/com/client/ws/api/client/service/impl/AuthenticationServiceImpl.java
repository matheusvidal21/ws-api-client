package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.dto.LoginDto;
import com.client.ws.api.client.dto.TokenDto;
import com.client.ws.api.client.exception.BadRequestException;
import com.client.ws.api.client.service.AuthenticationService;
import com.client.ws.api.client.service.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public TokenDto auth(LoginDto dto) {
        var userPassAuth = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());

        try{
            Authentication auth = authenticationManager.authenticate(userPassAuth);
            String token = jwtTokenService.generateToken(auth);
            return TokenDto.builder()
                    .token(token)
                    .type("Bearer")
                    .build();
        } catch (Exception e) {
            throw new BadRequestException("error when trying to format the token\n");
        }
    }
}
