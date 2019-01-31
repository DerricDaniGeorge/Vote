package com.derric.vote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.derric.vote.dao.IUserDBService;
import com.derric.vote.dao.UserDBService;
import com.derric.vote.services.UserServices;

@Configuration
public class UserConfig {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public UserServices userServices() {
		return new UserServices();
	}

	@Bean
	public IUserDBService iUserDBService() {
		return new UserDBService(jdbcTemplate);
	}

}
