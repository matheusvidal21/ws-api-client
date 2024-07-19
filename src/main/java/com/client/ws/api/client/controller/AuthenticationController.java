package com.client.ws.api.client.controller;

import com.client.ws.api.client.dto.LoginDto;
import com.client.ws.api.client.dto.TokenDto;
import com.client.ws.api.client.service.AuthenticationService;
import com.client.ws.api.client.service.JwtTokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginDto dto){
        return ResponseEntity.ok(authenticationService.auth(dto));
    }

}
