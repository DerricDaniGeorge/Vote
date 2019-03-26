package com.derric.vote.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.derric.vote.forms.AdminPartyForm;

public class AdminPartyValidator implements Validator {
	@Autowired
	CoreValidator coreValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return AdminPartyForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdminPartyForm partyForm=(AdminPartyForm)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "party", "required.party", "Party name required");
		coreValidator.rejectNotStringContainsOnlyAlphabetsIgnoreSpace(errors, partyForm.getParty().trim(), "party", "party.othercharacters", "Party must contains only letters");
	}
}
