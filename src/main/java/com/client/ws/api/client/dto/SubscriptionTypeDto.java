package com.client.ws.api.client.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionTypeDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters")
    private String name;

    @Max(value = 12, message = "Access month must be less than 12")
    private Long accessMonths;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotBlank(message = "Product key is required")
    @Size(min = 5, max = 15, message = "Product key must be between 5 and 15 characters")
    private String productKey;

}
