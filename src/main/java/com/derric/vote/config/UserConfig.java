package com.derric.vote.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.datastax.driver.core.Session;
import com.derric.vote.dao.CandidateDBServiceCassandra;
import com.derric.vote.dao.ElectionDBServiceCassandra;
import com.derric.vote.dao.ICandidateDBService;
import com.derric.vote.dao.IElectionDBService;
import com.derric.vote.dao.IUserDBService;
import com.derric.vote.dao.UserDBServiceCassandra;
import com.derric.vote.services.CandidateServices;
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
	public ICandidateDBService iCandidateDBService() {
		return new CandidateDBServiceCassandra(session);
	}
	@Bean 
	public ElectionServices electionServices() {
		return new ElectionServices();
	}
	@Bean
	public CandidateServices candidateServices() {
		return new CandidateServices();
	}
}
