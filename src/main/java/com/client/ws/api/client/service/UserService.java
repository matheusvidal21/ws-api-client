package com.client.ws.api.client.service;

import com.client.ws.api.client.dto.UserDto;
import com.client.ws.api.client.model.User;

public interface UserService {

    User create(UserDto dto);
}
