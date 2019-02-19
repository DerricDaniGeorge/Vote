package com.derric.vote.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.datastax.driver.core.exceptions.NoHostAvailableException;

@ControllerAdvice(basePackages= {"com.derric.vote.controllers","com.derric.vote.dao","com.derric.vote.exceptions","com.derric.vote.beans","com.derric.vote.services"})
public class ExceptionHandlingAdvice {

	private static final Logger logger = LogManager.getLogger("GLOBAL");

	@ExceptionHandler(NoHostAvailableException.class)
	public String handle(NoHostAvailableException nhae) {
		logger.error("Couldn't communicate with database",nhae);
		return "dbConnectionError";
	}

	@ExceptionHandler
	public String defaultError(Exception ex) {
		logger.error("Unexpected things occured.",ex);
		return "defaultError";
	}
}
