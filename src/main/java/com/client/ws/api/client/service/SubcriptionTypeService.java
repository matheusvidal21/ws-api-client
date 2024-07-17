package com.client.ws.api.client.service;

import com.client.ws.api.client.dto.SubscriptionTypeDto;
import com.client.ws.api.client.model.SubscriptionType;

import java.util.List;

public interface SubcriptionTypeService {

    List<SubscriptionType> findAll();

    SubscriptionType findById(Long id);

    SubscriptionType create(SubscriptionTypeDto dto);

    SubscriptionType update(Long id, SubscriptionTypeDto dto);

    void delete(Long id);
}
