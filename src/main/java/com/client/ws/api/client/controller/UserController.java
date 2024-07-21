package com.client.ws.api.client.controller;

import com.client.ws.api.client.dto.UserDto;
import com.client.ws.api.client.model.jpa.User;
import com.client.ws.api.client.service.UserService;
import com.client.ws.api.client.service.impl.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

}
