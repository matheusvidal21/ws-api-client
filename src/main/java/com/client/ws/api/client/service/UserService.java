package com.client.ws.api.client.service;

import com.client.ws.api.client.dto.UserDto;
import com.client.ws.api.client.model.jpa.User;

public interface UserService {

    User create(UserDto dto);

}
