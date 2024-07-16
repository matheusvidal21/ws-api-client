package com.client.ws.api.client.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subscription_type_id")
    private Long id;

    private String name;

    @Column(name = "access_month")
    private Long accessMonth;

    private BigDecimal price;

    @Column(name = "product_key")
    private String productKey;

}
