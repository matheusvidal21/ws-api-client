package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.dto.UserDto;
import com.client.ws.api.client.exception.BadRequestException;
import com.client.ws.api.client.exception.NotFoundException;
import com.client.ws.api.client.mapper.UserMapper;
import com.client.ws.api.client.model.jpa.User;
import com.client.ws.api.client.model.jpa.UserType;
import com.client.ws.api.client.model.redis.UserRecoveryCode;
import com.client.ws.api.client.repository.jpa.UserRepository;
import com.client.ws.api.client.repository.jpa.UserTypeRepository;
import com.client.ws.api.client.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.api.client.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserTypeRepository userTypeRepository;

    public UserServiceImpl(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }


    @Override
    public User create(UserDto dto) {
       if (Objects.nonNull(dto.getId())) {
           throw new BadRequestException("User id must be null");
       }

       var userTypeOpt = userTypeRepository.findById(dto.getUserTypeId());

       if (userTypeOpt.isEmpty()) {
           throw new NotFoundException("User type not found");
       }

       UserType userType = userTypeOpt.get();

       return userRepository.save(UserMapper.fromDtoToEntity(dto, userType, null));
    }

}
