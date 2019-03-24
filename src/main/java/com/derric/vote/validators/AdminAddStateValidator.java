package com.derric.vote.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.derric.vote.forms.AdminAddStateForm;

public class AdminAddStateValidator implements Validator{
	@Autowired
	CoreValidator coreValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return AdminAddStateForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdminAddStateForm addStateForm=(AdminAddStateForm)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "required.state", "State name required");
		coreValidator.rejectNotStringContainsOnlyAlphabetsIgnoreSpace(errors, addStateForm.getState().trim(), "state", "state.othercharacters", "State must contains only letters");
	}
}
