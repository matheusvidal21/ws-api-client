package com.client.ws.api.client.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String cpf;

    @Column(name = "dt_subscription")
    private LocalDate dtSubscription = LocalDate.now();

    @Column(name = "dt_expiration")
    private LocalDate dtExpiration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_type_id")
    private SubscriptionType subscriptionType;

}
