package com.derric.vote.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserConfig {
	private @Value("${db.username}") String dBUserName;
	private @Value("${db.password}") String dBPassword;
	
	
}
