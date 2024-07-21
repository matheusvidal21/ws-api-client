package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.model.jpa.UserCredentials;
import com.client.ws.api.client.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${webservices.rasplus.jwt.secret}")
    private String jwtSecret;

    @Value("${webservices.rasplus.jwt.expiration}")
    private Long jwtExpiration;

    private SecretKey key;

    @PostConstruct
    public void init(){
        if (jwtSecret == null || jwtSecret.length() < 64) {
            this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        } else {
            this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        }
    }

    @Override
    public String generateToken(Authentication auth) {
        UserCredentials user = (UserCredentials) auth.getPrincipal();

        return Jwts.builder()
                .setIssuer("API - Client")
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            getClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Long getUserId(String token) {
        Jws<Claims> claims = getClaimsJws(token);
         return Long.parseLong(claims.getBody().getSubject());
    }

    private Jws<Claims> getClaimsJws(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

}
