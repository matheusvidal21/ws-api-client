package com.client.ws.api.client.service;

import com.client.ws.api.client.dto.LoginDto;
import com.client.ws.api.client.dto.TokenDto;

public interface AuthenticationService {

    TokenDto auth(LoginDto dto);

}
