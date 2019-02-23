package com.derric.vote.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.derric.vote.beans.User;
import com.derric.vote.beans.UserDetail;
import com.derric.vote.forms.RegisterUserForm;
import com.derric.vote.services.UserServices;
import com.derric.vote.utils.MailSender;
import com.derric.vote.utils.OTPExpirer;
import com.derric.vote.utils.OTPGenerator;
import com.derric.vote.validators.RegisterUserValidator;

@Controller
@SessionAttributes(value = { "registerUserForm", "user"}) //Because we added these models to session , we can access these objects in any page until session is invaliated.
public class RegistrationController {
	
	@Autowired
	private RegisterUserValidator registerUserValidator;
	@Autowired
	private OTPGenerator otpGenerator;
	@Autowired
	private MailSender mailSender;
	@Autowired
	private UserServices userServices;
	@Autowired
	private OTPExpirer otpExpirer;

	@RequestMapping(value = { "/registerUser" }, method = RequestMethod.GET)
	public String showRegistrationPage(Model model) {
		RegisterUserForm registerUserForm = new RegisterUserForm();
		model.addAttribute("registerUserForm", registerUserForm);
		return "registrationForm";
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String submitForm(SessionStatus status, HttpServletRequest request, Model model,HttpSession session,
			@ModelAttribute("registerUserForm") @Validated RegisterUserForm registerUserForm, BindingResult result) {
		if (result.hasErrors()) {
			return "registrationForm";
		} else {
			User user = new User();
			user.setVotersId(registerUserForm.getVotersID().trim());
			user.setDetail(UserDetail.EMAIL, registerUserForm.getEmail().trim());
			if (!userServices.isUserAlreadyExist(user)) {
				model.addAttribute("user", user);
				String otp = otpGenerator.generateOTP();
				System.out.println("/registerUser post: "+otp);
				session.setAttribute("otp", otp);
				otpExpirer.expireOTP(session,otp);
				session.setAttribute("otpCount", 1);
				// mailSender.sendOTP(registerUserForm.getEmail().trim(),otp); //Uncomment this
				// line later
				model.addAttribute("session",session);
				return "otpForm";
			} else {
				model.addAttribute("msgUserExists", "User with Voter's ID: " + registerUserForm.getVotersID()
						+ " or email id: " + registerUserForm.getEmail() + " already exists.");
				return "registrationForm";
			}
		}
	}

	@RequestMapping(value = "/AccountCreatedSuccess")
	public String showSuccessMsg() {
		return "accountCreatedSuccess";
	}

/*	@RequestMapping(value = "/OTPForm", method = RequestMethod.GET)    This code is for testing purpose, delete it once developed
	public String showOTPForm(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("otpCount", 1); // This line is for testing purpose. Comment this line later
		return "otpForm";
	} */

	@RequestMapping(value = "/OTPForm", method = RequestMethod.POST)
	public String verifyOTP(HttpServletRequest request,
			@ModelAttribute("registerUserForm") RegisterUserForm registerUserForm, @ModelAttribute("user") User user,HttpSession session,
			Model model, SessionStatus status) {
		String otp = request.getParameter("otp");
		System.out.println("/OTPForm POST, session id: "+session.getId());
		String otpInSession = (String) session.getAttribute("otp");
		System.out.println(" Entered otp:" + otp + "-->otpinsession:" + otpInSession);
		if (otpInSession != null && otp.equals(otpInSession)) {
			System.out.println("otp:" + otp + "-->otpinsession:" + otpInSession);
			userServices.addUser(user, registerUserForm);
			
			otpExpirer.cancelTimer();
			status.setComplete();
			session.invalidate();
			return "redirect:AccountCreatedSuccess";
		}
		model.addAttribute("invalidOTP", "Invalid OTP");
		return "otpForm";
	}

	@RequestMapping(value = "/generateOTP",method=RequestMethod.GET)
	public String generateOTP(HttpServletRequest request, Model model,HttpSession session) {
		System.out.println("/generateOTP GET,session id: "+session.getId());
		System.out.println("Inside generateOTP get,otp in session: "+session.getAttribute("otp"));
		otpExpirer.expireOTPNow(session); //  invalidate existing OTP 
		System.out.println("Inside generateOTP get,otp in session after calling expireOTPNow: "+session.getAttribute("otp"));
		String otp = otpGenerator.generateOTP();
		session.setAttribute("otp", otp);
		System.out.println("New otp: "+otp+" session otp set as:"+session.getAttribute("otp"));
		int otpCount = (int) session.getAttribute("otpCount");
		otpCount++;
		session.setAttribute("otpCount", otpCount);
		System.out.println("Inside generateOTP get,otp in session after setting it again: "+session.getAttribute("otp"));
		otpExpirer.expireOTP(session,otp);
		return "otpForm";
	}

	@InitBinder("registerUserForm") // if values in bracket here is not specified,spring will use this validator for
									// all objects added to model object and will get illegalArgumentException.
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(registerUserValidator);
	}
}
