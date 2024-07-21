package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.dto.SubscriptionTypeDto;
import com.client.ws.api.client.exception.BadRequestException;
import com.client.ws.api.client.exception.NotFoundException;
import com.client.ws.api.client.mapper.SubscriptionTypeMapper;
import com.client.ws.api.client.model.jpa.SubscriptionType;
import com.client.ws.api.client.repository.jpa.SubscriptionTypeRepository;
import com.client.ws.api.client.service.SubcriptionTypeService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable("subscriptionType")
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    @Cacheable(value = "subscriptionType", key = "#id")
    public SubscriptionType findById(Long id) {
        return getSubscriptionType(id);
    }

    @Override
    @CacheEvict(value = "subscriptionType", allEntries = true)
    public SubscriptionType create(SubscriptionTypeDto dto) {
        if (Objects.nonNull(dto.getId())) {
            throw new BadRequestException("Id must be null");
        }
        return subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(dto));
    }

    @Override
    @CacheEvict(value = "subscriptionType", allEntries = true)
    public SubscriptionType update(Long id, SubscriptionTypeDto dto) {
        getSubscriptionType(id);
        dto.setId(id);
        return subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(dto));
    }

    @Override
    @CacheEvict(value = "subscriptionType", allEntries = true)
    public void delete(Long id) {
        getSubscriptionType(id);
        subscriptionTypeRepository.deleteById(id);
    }

    private SubscriptionType getSubscriptionType(Long id) {
        Optional<SubscriptionType> optionalSubscriptionType = subscriptionTypeRepository.findById(id);
        if (optionalSubscriptionType.isEmpty()){
            throw new NotFoundException("Subscription Type not found");
        }
        return optionalSubscriptionType.get();
    }
}
