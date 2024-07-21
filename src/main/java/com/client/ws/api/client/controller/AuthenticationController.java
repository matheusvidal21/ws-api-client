package com.client.ws.api.client.controller;

import com.client.ws.api.client.dto.LoginDto;
import com.client.ws.api.client.dto.TokenDto;
import com.client.ws.api.client.model.redis.UserRecoveryCode;
import com.client.ws.api.client.service.AuthenticationService;
import com.client.ws.api.client.service.JwtTokenService;
import com.client.ws.api.client.service.impl.UserDetailsServiceImpl;
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
    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationController(AuthenticationService authenticationService, UserDetailsServiceImpl userDetailsService) {
        this.authenticationService = authenticationService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginDto dto){
        return ResponseEntity.ok(authenticationService.auth(dto));
    }

    @PostMapping("/recovery-code/send")
    public ResponseEntity<Void> sendRecoveryCode(@RequestBody @Valid UserRecoveryCode userRecoveryCode){
        userDetailsService.sendRecoveryCode(userRecoveryCode.getEmail());
        return ResponseEntity.ok().build();
    }

}
