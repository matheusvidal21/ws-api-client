package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.dto.UserDetailsDto;
import com.client.ws.api.client.exception.NotFoundException;
import com.client.ws.api.client.integration.MailIntegration;
import com.client.ws.api.client.model.redis.UserRecoveryCode;
import com.client.ws.api.client.repository.jpa.UserDetailsRepository;
import com.client.ws.api.client.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.api.client.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${webservices.rasplus.redis.recoverycode.timeout.minutes}")
    private String recoveryCodeTimeout;


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

    public Boolean recoveryCodeIsValid(String code, String email){
        UserRecoveryCode userRecoveryCode = this.userRecoveryCodeRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));

        LocalDateTime timeout = userRecoveryCode.getCreatedAt().plusMinutes(Long.parseLong(recoveryCodeTimeout));
        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(timeout))
            return false;

        return code.equals(userRecoveryCode.getCode());
    }

    public void updatePasswordByRecoveryCode(UserDetailsDto dto){
        if (recoveryCodeIsValid(dto.getRecoveryCode(), dto.getEmail())){
            var userDetails = userDetailsRepository.findByUsername(dto.getEmail()).orElseThrow(() -> new NotFoundException("User not found"));
            userDetails.setPassword(PasswordUtils.encode(dto.getPassword()));
            userDetailsRepository.save(userDetails);
            this.mailIntegration.send(dto.getEmail(), "Password updated", "Your password has been updated");
        }
    }

}
