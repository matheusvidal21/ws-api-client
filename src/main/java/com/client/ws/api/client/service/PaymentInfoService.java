package com.client.ws.api.client.service;

import com.client.ws.api.client.dto.PaymentProcessDto;

public interface PaymentInfoService {

    Boolean process(PaymentProcessDto paymentProcessDto);

}
