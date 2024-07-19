package com.client.ws.api.client.service.impl;

import com.client.ws.api.client.exception.NotFoundException;
import com.client.ws.api.client.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDetailsOpt = userDetailsRepository.findByUsername(username);

        if(userDetailsOpt.isPresent())
            return userDetailsOpt.get();

        throw new NotFoundException("User not found");
    }

}
