package com.client.ws.api.client.mapper.wsraspay;

import com.client.ws.api.client.dto.wsraspay.CreditCardDto;
import com.client.ws.api.client.dto.wsraspay.PaymentDto;

public class PaymentMapper {

    public static PaymentDto build(String customerId, String orderId, CreditCardDto creditCardDto){
        return PaymentDto.builder()
                .customerId(customerId)
                .orderId(orderId)
                .creditCard(creditCardDto)
                .build();
    }

}
