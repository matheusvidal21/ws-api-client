package com.client.ws.api.client.mapper.wsraspay;

import com.client.ws.api.client.dto.wsraspay.CustomerDto;
import com.client.ws.api.client.model.jpa.User;

public class CustomerMapper {

    public static CustomerDto build(User user){
        var fullName = user.getName().split(" ");
        var firstName = fullName[0];
        var lastName = fullName.length > 1 ? fullName[fullName.length - 1] : "Unknown";
        return CustomerDto.builder()
                .firstName(firstName)
                .lastName(lastName)
                .cpf(user.getCpf())
                .email(user.getEmail())
                .build();
    }


}
