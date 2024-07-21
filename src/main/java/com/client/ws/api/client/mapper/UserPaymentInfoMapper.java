package com.client.ws.api.client.mapper;

import com.client.ws.api.client.dto.UserPaymentInfoDto;
import com.client.ws.api.client.model.jpa.User;
import com.client.ws.api.client.model.jpa.UserPaymentInfo;

public class UserPaymentInfoMapper {

    public static UserPaymentInfo fromDtoToEntity(UserPaymentInfoDto dto, User user) {
        return UserPaymentInfo.builder()
                .id(dto.getId())
                .cardNumber(dto.getCardNumber())
                .dtPayment(dto.getDtPayment())
                .cardExpirationMonth(dto.getCardExpirationMonth())
                .cardExpirationYear(dto.getCardExpirationYear())
                .cardSecurityCode(dto.getCardSecurityCode())
                .price(dto.getPrice())
                .dtPayment(dto.getDtPayment())
                .installments(dto.getInstallments())
                .user(user)
                .build();
    }
}
