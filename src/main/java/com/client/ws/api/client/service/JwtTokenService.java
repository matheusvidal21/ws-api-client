package com.client.ws.api.client.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenService {

    String generateToken(Authentication auth);

    Boolean validateToken(String token);

    Long getUserId(String token);
}
