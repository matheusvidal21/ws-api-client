package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.dto.PaymentProcessDto;
import com.client.ws.api.client.dto.wsraspay.CustomerDto;
import com.client.ws.api.client.dto.wsraspay.OrderDto;
import com.client.ws.api.client.dto.wsraspay.PaymentDto;
import com.client.ws.api.client.exception.BusinessException;
import com.client.ws.api.client.exception.NotFoundException;
import com.client.ws.api.client.integration.MailIntegration;
import com.client.ws.api.client.integration.WsRaspayIntegration;
import com.client.ws.api.client.mapper.UserPaymentInfoMapper;
import com.client.ws.api.client.mapper.wsraspay.CreditCardMapper;
import com.client.ws.api.client.mapper.wsraspay.CustomerMapper;
import com.client.ws.api.client.mapper.wsraspay.OrderMapper;
import com.client.ws.api.client.mapper.wsraspay.PaymentMapper;
import com.client.ws.api.client.model.User;
import com.client.ws.api.client.model.UserPaymentInfo;
import com.client.ws.api.client.repository.UserPaymentInfoRepository;
import com.client.ws.api.client.repository.UserRepository;
import com.client.ws.api.client.service.PaymentInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;
    private final MailIntegration mailIntegration;

    public PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository, WsRaspayIntegration wsRaspayIntegration, MailIntegration mailIntegration) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
    }

    @Override
    public Boolean process(PaymentProcessDto paymentProcessDto) {
        var userOpt = userRepository.findById(paymentProcessDto.getUserPaymentInfoDto().getUserId());
        if (userOpt.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        User user = userOpt.get();
        if(Objects.nonNull(user.getSubscriptionType())){
            throw new BusinessException("User already has a subscription");
        }

        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), paymentProcessDto));
        PaymentDto paymentDto = PaymentMapper.build(customerDto.getId(), orderDto.getId(), CreditCardMapper.build(paymentProcessDto.getUserPaymentInfoDto(), customerDto.getCpf()));
        Boolean successPayment = wsRaspayIntegration.processPayment(paymentDto);

        if (Boolean.TRUE.equals(successPayment)) {
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(paymentProcessDto.getUserPaymentInfoDto(), user);
            userPaymentInfoRepository.save(userPaymentInfo);

            mailIntegration.send(user.getEmail(), "Access granted", String.format("User: %s - Password (default): rasmoo123", user.getEmail()));
            return true;
        }
        return false;
    }
}
