package com.client.ws.api.client.controller;

import com.client.ws.api.client.model.SubscriptionType;
import com.client.ws.api.client.service.SubcriptionTypeService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscription-type")
public class SubscriptionTypeController {

    private final SubcriptionTypeService subscriptionTypeService;

    public SubscriptionTypeController(SubcriptionTypeService subscriptionTypeService) {
        this.subscriptionTypeService = subscriptionTypeService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionType>> findAll() {
        return ResponseEntity.ok(subscriptionTypeService.findAll());
    }

}
