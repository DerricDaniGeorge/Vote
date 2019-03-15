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
import com.derric.vote.beans.VoteConstants;
import com.derric.vote.constants.PageConstants;
import com.derric.vote.constants.URLConstants;
import com.derric.vote.forms.LoginForm;
import com.derric.vote.services.UserServices;
import com.derric.vote.validators.LoginFormValidator;

@Controller
public class LoginController {
	

	@Autowired
	private UserServices userServices;
	@Autowired
	private LoginFormValidator loginFormValidator;

	@RequestMapping(value = "/")
	public String showWelcomePage() {
		return "redirect:/"+URLConstants.LOGIN;
	}

	@RequestMapping(value = "/"+URLConstants.LOGIN, method = RequestMethod.GET)
	public String showLoginPage(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return PageConstants.LOGIN_PAGE;
	}

	@RequestMapping(value = "/"+URLConstants.LOGIN, method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model,
			@ModelAttribute("loginForm") @Validated LoginForm loginForm, BindingResult result) {
		if (result.hasErrors()) {
			return PageConstants.LOGIN_PAGE;
		}
		User user = new User();
		user.setVotersId(loginForm.getVotersID());
		user.setPassword(loginForm.getPassword());
		String role=userServices.doLogin(user);
		if (role!=null) {
			request.getSession().setAttribute("user", user);
			if(role.equalsIgnoreCase(VoteConstants.ADMIN.toString())) {
			return "redirect:/"+URLConstants.ADMIN_CANDIDATE;
			}else {
				return PageConstants.CAST_VOTE_PAGE;
			}
		}
		model.addAttribute("invalidCredentials", "Invalid username or password");
		return "login";
	}

	@InitBinder("loginForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(loginFormValidator);
	}

}
