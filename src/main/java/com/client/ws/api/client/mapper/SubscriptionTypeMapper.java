package com.client.ws.api.client.mapper;

import com.client.ws.api.client.dto.SubscriptionTypeDto;
import com.client.ws.api.client.model.jpa.SubscriptionType;

public class SubscriptionTypeMapper {

    public static SubscriptionType fromDtoToEntity(SubscriptionTypeDto dto){
        return SubscriptionType.builder()
                .id(dto.getId())
                .name(dto.getName())
                .accessMonths(dto.getAccessMonths())
                .price(dto.getPrice())
                .productKey(dto.getProductKey())
                .build();
    }

    public static SubscriptionTypeDto fromEntityToDto(SubscriptionType entity){
        return SubscriptionTypeDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .accessMonths(entity.getAccessMonths())
                .price(entity.getPrice())
                .productKey(entity.getProductKey())
                .build();
    }

}
