package com.derric.vote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Session;
import com.derric.vote.dao.ElectionDBServiceCassandra;
import com.derric.vote.dao.IElectionDBService;
import com.derric.vote.dao.IUserDBService;
import com.derric.vote.dao.UserDBServiceCassandra;
import com.derric.vote.services.ElectionServices;
import com.derric.vote.services.UserServices;

@Configuration
public class UserConfig {

	  @Autowired 
	  private Session session;
	  

	@Bean
	public UserServices userServices() {
		return new UserServices();
	}

	@Bean
	public IUserDBService iUserDBService() {
		return new UserDBServiceCassandra(session);
	}

	@Bean
	public IElectionDBService iElectionDBService() {
		return new ElectionDBServiceCassandra(session);
	}
	@Bean 
	public ElectionServices electionServices() {
		return new ElectionServices();
	}
}
