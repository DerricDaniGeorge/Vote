package com.derric.vote.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.derric.vote.dao.JdbcUserDaoImpl;
import com.derric.vote.dao.UserDAO;
import com.derric.vote.services.UserServices;


@Configuration
public class UserConfig {
	private @Value("${db.username}") String dBUserName;
	private @Value("${db.password}") String dBPassword;
	
	
}
