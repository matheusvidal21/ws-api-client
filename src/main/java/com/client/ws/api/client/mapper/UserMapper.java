package com.client.ws.api.client.mapper;

import com.client.ws.api.client.dto.UserDto;
import com.client.ws.api.client.model.jpa.SubscriptionType;
import com.client.ws.api.client.model.jpa.User;
import com.client.ws.api.client.model.jpa.UserType;

public class UserMapper {

    public static User fromDtoToEntity(UserDto dto, UserType userType, SubscriptionType subscriptionType){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .cpf(dto.getCpf())
                .dtSubscription(dto.getDtSubscription())
                .dtExpiration(dto.getDtExpiration())
                .userType(userType)
                .subscriptionType(subscriptionType)
                .build();
    }

    public static UserDto fromEntityToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .cpf(user.getCpf())
                .dtSubscription(user.getDtSubscription())
                .dtExpiration(user.getDtExpiration())
                .userTypeId(user.getUserType().getId())
                .subscriptionTypeId(user.getSubscriptionType().getId())
                .build();
    }
}
