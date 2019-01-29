package com.derric.vote.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.derric.vote.beans.User;
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
		return "redirect:/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "Login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model,
			@ModelAttribute("loginForm") @Validated LoginForm loginForm, BindingResult result) {
		if (result.hasErrors()) {
			return "Login";
		}
		User user = new User();
		user.setVotersId(request.getParameter("votersId"));
		user.setPassword(request.getParameter("password"));
		if (userServices.doLogin(user)) {
			return "CastVote";
		}
		model.addAttribute("invalidCredentials", "Invalid username or password");
		return "Login";
	}

	@InitBinder("loginForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(loginFormValidator);
	}
}
