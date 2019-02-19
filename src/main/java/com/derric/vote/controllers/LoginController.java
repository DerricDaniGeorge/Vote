package com.derric.vote.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.derric.vote.beans.User;
import com.derric.vote.forms.LoginForm;
import com.derric.vote.services.UserServices;
import com.derric.vote.validators.LoginFormValidator;

@Controller
public class LoginController {
	
	private static final Logger logger = LogManager.getLogger("GLOBAL");

	@Autowired
	private UserServices userServices;
	@Autowired
	private LoginFormValidator loginFormValidator;

	@RequestMapping(value = "/")
	public String showWelcomePage() {
		return "redirect:/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model,
			@ModelAttribute("loginForm") @Validated LoginForm loginForm, BindingResult result) {
		if (result.hasErrors()) {
			return "login";
		}
		User user = new User();
		user.setVotersId(loginForm.getVotersID());
		user.setPassword(loginForm.getPassword());
		if (userServices.doLogin(user)) {
			return "castVote";
		}
		model.addAttribute("invalidCredentials", "Invalid username or password");
		return "login";
	}

	@InitBinder("loginForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(loginFormValidator);
	}
/*	
	@ExceptionHandler(NoHostAvailableException.class)
	public String handle(NoHostAvailableException dae) {
		logger.error("Couldn't communicate with database");
		logger.error(dae);
		return "dbConnectionError";
	}

	@ExceptionHandler
	public String defaultError(Exception ex) {
		logger.error(ex);
		return "defaultError";
	}  */

}
