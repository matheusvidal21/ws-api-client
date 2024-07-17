package com.client.ws.api.client.controller;

import com.client.ws.api.client.dto.SubscriptionTypeDto;
import com.client.ws.api.client.model.SubscriptionType;
import com.client.ws.api.client.service.SubcriptionTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionType> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(subscriptionTypeService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubscriptionType> create(@Valid @RequestBody SubscriptionTypeDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionTypeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionType> update(@PathVariable("id") Long id, @RequestBody SubscriptionTypeDto dto) {
        return ResponseEntity.ok(subscriptionTypeService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        subscriptionTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
