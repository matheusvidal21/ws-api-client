package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.dto.SubscriptionTypeDto;
import com.client.ws.api.client.exception.BadRequestException;
import com.client.ws.api.client.exception.NotFoundException;
import com.client.ws.api.client.model.SubscriptionType;
import com.client.ws.api.client.repository.SubscriptionTypeRepository;
import com.client.ws.api.client.service.SubcriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubcriptionTypeServiceImpl implements SubcriptionTypeService {

    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public SubcriptionTypeServiceImpl(SubscriptionTypeRepository subscriptionTypeRepository) {
        this.subscriptionTypeRepository = subscriptionTypeRepository;
    }

    @Override
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    public SubscriptionType findById(Long id) {
        Optional<SubscriptionType> optionalSubscriptionType = subscriptionTypeRepository.findById(id);
        if (optionalSubscriptionType.isEmpty()){
             throw new NotFoundException("Subscription Type not found");
        }
        return optionalSubscriptionType.get();
    }

    @Override
    public SubscriptionType create(SubscriptionTypeDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("Id must be null");
        }
        return subscriptionTypeRepository.save(SubscriptionType.builder()
                .id(null)
                .name(dto.getName())
                .accessMonth(dto.getAccessMonth())
                .price(dto.getPrice())
                .productKey(dto.getProductKey())
                .build()
        );
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionType subscriptionType) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
