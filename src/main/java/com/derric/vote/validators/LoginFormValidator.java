package com.derric.vote.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.derric.vote.forms.LoginForm;

public class LoginFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return LoginForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "votersID", "required.votersID", "Voter's ID required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password", "Password required");
	}
}
