package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.model.SubscriptionType;
import com.client.ws.api.client.repository.SubscriptionTypeRepository;
import com.client.ws.api.client.service.SubcriptionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public SubscriptionType create(SubscriptionType subscriptionType) {
        return null;
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionType subscriptionType) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
