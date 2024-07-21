package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.dto.PaymentProcessDto;
import com.client.ws.api.client.dto.wsraspay.CustomerDto;
import com.client.ws.api.client.dto.wsraspay.OrderDto;
import com.client.ws.api.client.dto.wsraspay.PaymentDto;
import com.client.ws.api.client.enums.UserTypeEnum;
import com.client.ws.api.client.exception.BusinessException;
import com.client.ws.api.client.exception.NotFoundException;
import com.client.ws.api.client.integration.MailIntegration;
import com.client.ws.api.client.integration.WsRaspayIntegration;
import com.client.ws.api.client.mapper.UserPaymentInfoMapper;
import com.client.ws.api.client.mapper.wsraspay.CreditCardMapper;
import com.client.ws.api.client.mapper.wsraspay.CustomerMapper;
import com.client.ws.api.client.mapper.wsraspay.OrderMapper;
import com.client.ws.api.client.mapper.wsraspay.PaymentMapper;
import com.client.ws.api.client.model.*;
import com.client.ws.api.client.repository.*;
import com.client.ws.api.client.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Value("${webservices.rasplus.default.password}")
    private String defaultPassword;

    private final UserRepository userRepository;
    private final UserPaymentInfoRepository userPaymentInfoRepository;
    private final WsRaspayIntegration wsRaspayIntegration;
    private final MailIntegration mailIntegration;
    private final UserDetailsRepository userDetailsRepository;
    private final UserTypeRepository userTypeRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;

    public PaymentInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository,
                                  WsRaspayIntegration wsRaspayIntegration, MailIntegration mailIntegration,
                                  UserDetailsRepository userDetailsRepository, UserTypeRepository userTypeRepository,
                                  SubscriptionTypeRepository subscriptionTypeRepository) {
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntegration = wsRaspayIntegration;
        this.mailIntegration = mailIntegration;
        this.userDetailsRepository = userDetailsRepository;
        this.userTypeRepository = userTypeRepository;
        this.subscriptionTypeRepository = subscriptionTypeRepository;
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

        Boolean successPayment = getSuccessPayment(paymentProcessDto, user);

        return createUserCredentials(paymentProcessDto, user, successPayment);
    }

    private boolean createUserCredentials(PaymentProcessDto paymentProcessDto, User user, Boolean successPayment) {
        if (Boolean.TRUE.equals(successPayment)) {
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(paymentProcessDto.getUserPaymentInfoDto(), user);
            this.userPaymentInfoRepository.save(userPaymentInfo);

            UserType userType = this.userTypeRepository.findById(UserTypeEnum.STUDENT.getId()).orElseThrow(() -> new NotFoundException("User type not found"));

            UserCredentials userCredentials = new UserCredentials(null, user.getEmail().trim(), new BCryptPasswordEncoder().encode(defaultPassword), userType);
            this.userDetailsRepository.save(userCredentials);

            SubscriptionType subscriptionType = this.subscriptionTypeRepository.findByProductKey(paymentProcessDto.getProductKey()).orElseThrow(() -> new NotFoundException("Subscription type not found"));

            user.setSubscriptionType(subscriptionType);
            this.userRepository.save(user);
            
            mailIntegration.send(user.getEmail(), "Access granted", String.format("User: %s - Password (default): rasmoo123", user.getEmail()));
            return true;
        }
        return false;
    }

    private Boolean getSuccessPayment(PaymentProcessDto paymentProcessDto, User user) {
        CustomerDto customerDto = wsRaspayIntegration.createCustomer(CustomerMapper.build(user));
        OrderDto orderDto = wsRaspayIntegration.createOrder(OrderMapper.build(customerDto.getId(), paymentProcessDto));
        PaymentDto paymentDto = PaymentMapper.build(customerDto.getId(), orderDto.getId(), CreditCardMapper.build(paymentProcessDto.getUserPaymentInfoDto(), customerDto.getCpf()));
        return wsRaspayIntegration.processPayment(paymentDto);
    }
}
