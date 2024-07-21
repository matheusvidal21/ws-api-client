package com.client.ws.api.client.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentInfoDto {

    private Long id;

    @Size(min = 16, max = 16, message = "Card number must have 16 digits")
    private String cardNumber;

    @Min(value = 1, message = "Card expiration month must be greater than 0")
    @Max(value = 12, message = "Card expiration month must be less than 13")
    private Long cardExpirationMonth;

    private Long cardExpirationYear;

    @Size(min = 3, max = 3, message = "Card security code must have 3 digits")
    private String cardSecurityCode;

    private BigDecimal price;

    private LocalDate dtPayment = LocalDate.now();

    private Long installments;

    @NotNull(message = "User id is required")
    private Long userId;

}
