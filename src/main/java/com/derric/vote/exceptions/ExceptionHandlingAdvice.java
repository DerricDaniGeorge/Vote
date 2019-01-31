package com.derric.vote.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingAdvice {

	private static final Logger logger = LogManager.getLogger("GLOBAL");

	@ExceptionHandler(DataAccessException.class)
	public String handle(DataAccessException dae) {
		logger.error("Couldn't communicate with database");
		logger.error(dae);
		return "dbConnectionError";
	}

	@ExceptionHandler
	public String defaultError(Exception ex) {
		logger.error(ex);
		return "defaultError";
	}
}
