package com.client.ws.api.client.integration;

import com.client.ws.api.client.dto.wsraspay.CreditCardDto;
import com.client.ws.api.client.dto.wsraspay.CustomerDto;
import com.client.ws.api.client.dto.wsraspay.OrderDto;
import com.client.ws.api.client.dto.wsraspay.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class MailIntegrationImplTest {

    @Autowired
    private MailIntegration mailIntegration;

    @Test
    void send() {
        mailIntegration.send("gomesisabela13@gmail.com", "Acesso liberado", "teste");
    }


}
