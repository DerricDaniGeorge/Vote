package com.derric.vote.controllers;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.derric.vote.forms.RegisterUserForm;
import com.derric.vote.utils.OTPGenerator;
import com.derric.vote.validators.RegisterUserValidator;

@Controller
@RequestMapping("/")
@SessionAttributes("registerUserForm")
public class VoteController {
	
	@Autowired
	private RegisterUserValidator registerUserValidator;
	@Autowired
	private OTPGenerator otpGenerator;
	
	
	@RequestMapping(value="/registerUser", method=RequestMethod.GET)
	public String showRegistrationPage(Model model) {
		RegisterUserForm registerUserForm=new RegisterUserForm();
		model.addAttribute("registerUserForm",registerUserForm);
	//	model.addAttribute("dateToSet",(LocalDate.of((LocalDate.now().minusYears(18)).getYear(),Month.JANUARY,1)).toString());
		return "RegistrationForm";
	}
	@RequestMapping(value="/registerUser", method=RequestMethod.POST)
	public String submitForm(@ModelAttribute("registerUserForm")
	@Validated RegisterUserForm registerUserForm,BindingResult result, SessionStatus status) {	
		if(result.hasErrors()) {
			return "RegistrationForm";
		}else {
			//status.setComplete();
		return "redirect:OTPForm";
		}
	}
	@RequestMapping(value="/AccountCreatedSuccess")
	public String showSuccessMsg() {
		return "AccountCreatedSuccess";
	}
	@RequestMapping(value="/OTPForm")
	public String showOTPForm() {
		return "OTPForm";
	}
	@RequestMapping(value="/generateOTP")
	public String generateOTP() {
			otpGenerator.generateOTP(6);
		return "redirect:AccountCreatedSuccess";
		}
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(registerUserValidator);
	}
}
