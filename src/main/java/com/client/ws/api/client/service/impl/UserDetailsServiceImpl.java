package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.exception.NotFoundException;
import com.client.ws.api.client.integration.MailIntegration;
import com.client.ws.api.client.model.redis.UserRecoveryCode;
import com.client.ws.api.client.repository.jpa.UserDetailsRepository;
import com.client.ws.api.client.repository.redis.UserRecoveryCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserRecoveryCodeRepository userRecoveryCodeRepository;
    private final MailIntegration mailIntegration;

    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository, UserRecoveryCodeRepository userRecoveryCodeRepository, MailIntegration mailIntegration) {
        this.userDetailsRepository = userDetailsRepository;
        this.userRecoveryCodeRepository = userRecoveryCodeRepository;
        this.mailIntegration = mailIntegration;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDetailsOpt = userDetailsRepository.findByUsername(username);

        if(userDetailsOpt.isPresent())
            return userDetailsOpt.get();

        throw new NotFoundException("User not found");
    }

    public void sendRecoveryCode(String email) {
        UserRecoveryCode userRecoveryCode;
        String code = String.format("%04d", new Random().nextInt(10000));

        var userRecoveryCodeOpt = this.userRecoveryCodeRepository.findByEmail(email);

        if (userRecoveryCodeOpt.isEmpty()){
            var user = userDetailsRepository.findByUsername(email).orElseThrow(() -> new NotFoundException("User not found"));

            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);
        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }

        userRecoveryCode.setCode(code);
        userRecoveryCode.setCreatedAt(LocalDateTime.now());
        this.userRecoveryCodeRepository.save(userRecoveryCode);
        this.mailIntegration.send(email, "Recovery code", "Your recovery code is: " + code);
    }

}
