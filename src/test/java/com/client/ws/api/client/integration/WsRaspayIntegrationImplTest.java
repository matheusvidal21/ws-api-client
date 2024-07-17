package com.client.ws.api.client.integration;

import com.client.ws.api.client.dto.wsraspay.CreditCardDto;
import com.client.ws.api.client.dto.wsraspay.CustomerDto;
import com.client.ws.api.client.dto.wsraspay.OrderDto;
import com.client.ws.api.client.dto.wsraspay.PaymentDto;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class WsRaspayIntegrationImplTest {

    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void createCustomerWhenDtoOk() {
        CustomerDto dto = new CustomerDto(null,  "Felipe", "Alves", "09601954325", "matheus@gmail.com");
        wsRaspayIntegration.createCustomer(dto);
    }

    @Test
    void createOrderWhenDtoOk() {
        OrderDto dto = new OrderDto(null, "66982b8778fb335d9de50d53", "MONTH22", BigDecimal.ZERO);
        wsRaspayIntegration.createOrder(dto);
    }

    @Test
    void processPaymentWhenDtoOk() {
        CreditCardDto creditCardDto = new CreditCardDto(123, "09601954325", 0L, 6, "1234123412341234", 2025L);
        PaymentDto paymentDto = new PaymentDto(creditCardDto, "66982b8778fb335d9de50d53", "66982e9978fb335d9de50d58");
        wsRaspayIntegration.processPayment(paymentDto);
    }

}
