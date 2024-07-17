package com.client.ws.api.client.dto;

import com.client.ws.api.client.model.SubscriptionType;
import com.client.ws.api.client.model.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 6, max = 100, message = "Name must be between 6 and 100 characters")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(min = 11, message = "Phone must be greater than 11 characters")
    private String phone;

    @CPF(message = "Invalid CPF")
    private String cpf;

    private LocalDate dtSubscription = LocalDate.now();

    private LocalDate dtExpiration = LocalDate.now();

    @NotNull(message = "User type is required")
    private Long userTypeId;

    private Long subscriptionTypeId;
}
