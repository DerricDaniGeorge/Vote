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
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import com.derric.vote.beans.User;
import com.derric.vote.beans.UserDetail;
import com.derric.vote.forms.RegisterUserForm;
import com.derric.vote.services.UserServices;
import com.derric.vote.utils.MailSender;
import com.derric.vote.utils.OTPExpirer;
import com.derric.vote.utils.OTPGenerator;
import com.derric.vote.validators.RegisterUserValidator;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
@SessionAttributes(value = { "registerUserForm", "user", "otp" })
public class VoteController {

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

	@RequestMapping(value = "/")
	public String showWelcomePage() {
		return "redirect:/registerUser";
	}

	@RequestMapping(value = { "/registerUser" }, method = RequestMethod.GET)
	public String showRegistrationPage(Model model) {
		RegisterUserForm registerUserForm = new RegisterUserForm();
		model.addAttribute("registerUserForm", registerUserForm);
		// model.addAttribute("dateToSet",(LocalDate.of((LocalDate.now().minusYears(18)).getYear(),Month.JANUARY,1)).toString());
		return "RegistrationForm";
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String submitForm(SessionStatus status, HttpServletRequest request, Model model,
			@ModelAttribute("registerUserForm") @Validated RegisterUserForm registerUserForm, BindingResult result) {
		if (result.hasErrors()) {
			return "RegistrationForm";
		} else {
			User user = new User();
			user.setVotersId(registerUserForm.getVotersID().trim());
			user.setDetail(UserDetail.EMAIL,registerUserForm.getEmail().trim());
			if (!userServices.isUserAlreadyExist(user)) {
				model.addAttribute("user", user);
				String otp = otpGenerator.generateOTP();
				model.addAttribute("otp", otp);
				// mailSender.sendOTP(registerUserForm.getEmail().trim(),otp);
				return "OTPForm";
			} else {
				model.addAttribute("msgUserExists",
						"User with Voter's ID: " + registerUserForm.getVotersID()+ " or email id: "+registerUserForm.getEmail()+" already exists.");
				return "RegistrationForm";
			}
			// status.setComplete();

		}
	}

	@RequestMapping(value = "/AccountCreatedSuccess")
	public String showSuccessMsg() {
		return "AccountCreatedSuccess";
	}

	/*
	 * @RequestMapping(value="/OTPForm", method=RequestMethod.GET) public String
	 * showOTPForm(@ModelAttribute("registerUserForm") RegisterUserForm
	 * registerUserForm ) { return "OTPForm"; }
	 */
	@RequestMapping(value = "/OTPForm", method = RequestMethod.POST)
	public String verifyOTP(HttpServletRequest request,
			@ModelAttribute("registerUserForm") RegisterUserForm registerUserForm, 
			@ModelAttribute("user") User user,@ModelAttribute("otp") String otpInSession) {
		String otp = request.getParameter("otp");
		if (otp.equals(otpInSession)) {
			System.out.println("otp:"+otp+"-->otpinsession:"+otpInSession);
			userServices.addUser(user, registerUserForm);
			return "AccountCreatedSuccess";
		} else {
			request.setAttribute("invalidOTP", "Invalid OTP");
			return "OTPForm";
		}
	}

	@RequestMapping(value = "/generateOTP")
	public String generateOTP(WebRequest request,Model model,SessionAttributeStore store) {
		String otp=otpGenerator.generateOTP();
		model.addAttribute("otp",otp);
		otpExpirer.expireOTP(otp, request, store);
		return "redirect:/OTPForm";
	}

	@InitBinder("registerUserForm") // if values in bracket here is not specified,spring will use this validator for
									// all objects added to model object and will get illegalArgumentException.
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(registerUserValidator);
	}
}
