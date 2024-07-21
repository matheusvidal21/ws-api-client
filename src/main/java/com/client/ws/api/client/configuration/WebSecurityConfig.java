package com.client.ws.api.client.configuration;

import com.client.ws.api.client.filter.AuthenticationFilter;
import com.client.ws.api.client.service.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationFilter authenticationFilter;

    public WebSecurityConfig(UserDetailsService userDetailsService, AuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/subscription-type").permitAll()
                        .requestMatchers(HttpMethod.GET, "/subscription-type/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .requestMatchers( "/auth/recovery-code/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.POST, "/payment/process").permitAll()
                        .requestMatchers("/h2-console/*").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(headers -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
