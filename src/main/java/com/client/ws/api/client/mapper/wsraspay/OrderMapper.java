package com.client.ws.api.client.mapper.wsraspay;

import com.client.ws.api.client.dto.PaymentProcessDto;
import com.client.ws.api.client.dto.wsraspay.CustomerDto;
import com.client.ws.api.client.dto.wsraspay.OrderDto;
import com.client.ws.api.client.model.User;

public class OrderMapper {

    public static OrderDto build(String customerId, PaymentProcessDto paymentProcessDto){
        return OrderDto.builder()
                .customerId(customerId)
                .productAcronym(paymentProcessDto.getProductKey())
                .discount(paymentProcessDto.getDiscount())
                .build();
    }


}
