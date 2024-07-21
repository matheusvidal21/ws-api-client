package com.client.ws.api.client;

import com.client.ws.api.client.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserDetailsServiceTest {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Test
	void contextLoads() {
		userDetailsService.sendRecoveryCode("gomesisabela13@gmail.com");
	}

}
