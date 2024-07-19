package com.client.ws.api.client.controller;

import com.client.ws.api.client.dto.PaymentProcessDto;
import com.client.ws.api.client.service.PaymentInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentInfoController {

    private PaymentInfoService paymentInfoService;

    public PaymentInfoController(PaymentInfoService paymentInfoService) {
        this.paymentInfoService = paymentInfoService;
    }

    @PostMapping("/process")
    public ResponseEntity<Boolean> process(@RequestBody PaymentProcessDto dto){
        return ResponseEntity.ok(paymentInfoService.process(dto));
    }


}
