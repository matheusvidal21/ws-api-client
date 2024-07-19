package com.client.ws.api.client.integration.impl;

import com.client.ws.api.client.dto.wsraspay.CustomerDto;
import com.client.ws.api.client.dto.wsraspay.OrderDto;
import com.client.ws.api.client.dto.wsraspay.PaymentDto;
import com.client.ws.api.client.exception.IntegrationException;
import com.client.ws.api.client.integration.WsRaspayIntegration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntegration {

    private RestTemplate restTemplate;

    private final HttpHeaders headers;

    @Value("${webservices.raspay.host}")
    private String WS_RASPAY_URL;

    @Value("${webservices.raspay.v1.customer}")
    private String CUSTOMER_URL;

    @Value("${webservices.raspay.v1.order}")
    private String ORDER_URL;

    @Value("${webservices.raspay.v1.payment}")
    private String PAYMENT_URL;

    public WsRaspayIntegrationImpl() {
        this.restTemplate = new RestTemplate();
        this.headers = getHttpHeaders();
    }

    @Override
    public CustomerDto createCustomer(CustomerDto dto) {
        try {
            HttpEntity<CustomerDto> request = new HttpEntity<>(dto, this.headers);
            ResponseEntity<CustomerDto> response = restTemplate.exchange(
                WS_RASPAY_URL + CUSTOMER_URL,
                HttpMethod.POST, request,
                CustomerDto.class);
            return response.getBody();

        } catch (HttpStatusCodeException e) {
            String errorMessage = "Error creating customer: " + e.getResponseBodyAsString();
            throw new IntegrationException(errorMessage, e);
        } catch (Exception e) {
            throw new IntegrationException("Error creating customer: " + e.getMessage(), e);
        }
    }

    @Override
    public OrderDto createOrder(OrderDto dto) {
        try {
            HttpEntity<OrderDto> request = new HttpEntity<>(dto, this.headers);
            ResponseEntity<OrderDto> response = restTemplate.exchange(
                    WS_RASPAY_URL + ORDER_URL,
                    HttpMethod.POST, request,
                    OrderDto.class);
            return response.getBody();

        } catch (HttpStatusCodeException e) {
            String errorMessage = "Error creating order: " + e.getResponseBodyAsString();
            throw new IntegrationException(errorMessage, e);
        } catch (Exception e) {
            throw new IntegrationException("Error creating order: " + e.getMessage(), e);
        }
    }

    @Override
    public Boolean processPayment(PaymentDto dto) {
        try {
            HttpEntity<PaymentDto> request = new HttpEntity<>(dto, this.headers);
            ResponseEntity<Boolean> response = restTemplate.exchange(
                    WS_RASPAY_URL + PAYMENT_URL,
                    HttpMethod.POST, request,
                    Boolean.class);
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            String errorMessage = "Error creating order: " + e.getResponseBodyAsString();
            throw new IntegrationException(errorMessage, e);
        } catch (Exception e) {
            throw new IntegrationException("Error creating order: " + e.getMessage(), e);
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String credential = "rasmooplus:r@sm00";
        String base64 = Base64.getEncoder().encodeToString(credential.getBytes(StandardCharsets.UTF_8));
        headers.add("Authorization", "Basic " + base64);
        return headers;
    }
}
