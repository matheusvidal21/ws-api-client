package com.client.ws.api.client.mapper.wsraspay;

import com.client.ws.api.client.dto.UserPaymentInfoDto;
import com.client.ws.api.client.dto.wsraspay.CreditCardDto;

public class CreditCardMapper {

    public static CreditCardDto build(UserPaymentInfoDto userPaymentInfoDto, String documentNumber){
        return CreditCardDto.builder()
                .documentNumber(documentNumber)
                .cvv(Integer.parseInt(userPaymentInfoDto.getCardSecurityCode()))
                .number(userPaymentInfoDto.getCardNumber())
                .month(userPaymentInfoDto.getCardExpirationMonth().intValue())
                .year(userPaymentInfoDto.getCardExpirationYear())
                .installments(userPaymentInfoDto.getInstallments())
                .build();
    }
}
