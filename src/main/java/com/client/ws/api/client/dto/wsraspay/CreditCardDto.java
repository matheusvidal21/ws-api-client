package com.client.ws.api.client.dto.wsraspay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDto {

    private Integer cvv;

    private String documentNumber;

    private Long installments;

    private Integer month;

    private String number;

    private Long year;

}
