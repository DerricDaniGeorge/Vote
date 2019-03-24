package com.derric.vote.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.derric.vote.forms.AddLokSabhaConstituencyForm;
import com.derric.vote.forms.AdminAddStateForm;

public class AddLokSabhaConstituencyValidator implements Validator {
	@Autowired
	CoreValidator coreValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return AddLokSabhaConstituencyForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AddLokSabhaConstituencyForm addLokSabhaConstituencyForm=(AddLokSabhaConstituencyForm)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "constituency", "required.constituency", "Constituency name required");
		coreValidator.rejectNotStringContainsOnlyAlphabetsIgnoreSpace(errors, addLokSabhaConstituencyForm.getConstituency().trim(), "constituency", "constituency.othercharacters", "Constituency must contains only letters");
	}
}
