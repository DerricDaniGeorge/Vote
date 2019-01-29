package com.derric.vote.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.derric.vote.services.UserServices;

@Configuration
public class UserConfig {

	@Bean
	public UserServices userServices() {
		return new UserServices();
	}
}
