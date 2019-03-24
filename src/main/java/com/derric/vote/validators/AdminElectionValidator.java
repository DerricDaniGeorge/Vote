package com.derric.vote.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.derric.vote.beans.ElectionDetail;
import com.derric.vote.forms.AdminElectionForm;
import com.derric.vote.forms.LoginForm;

public class AdminElectionValidator implements Validator {
	@Autowired
	CoreValidator coreValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return AdminElectionForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "electionName", "required.electionName", "Election Name required");
		
		AdminElectionForm electionForm=(AdminElectionForm) target;
		if (electionForm.getStartDate() == null) {
			errors.rejectValue("startDate", "invalid.startDate", "Please input start date");
		}
		if (electionForm.getEndDate() == null) {
			errors.rejectValue("endDate", "invalid.endDate", "Please input end date");
		}
		coreValidator.rejectNotStringMinCharacters(errors, electionForm.getElectionName().trim(), 5, "electionName", "electionName.minNoChars", "No. of characters cannot be lesser than 5.");
		coreValidator.rejectNotStringMaxCharacters(errors, electionForm.getElectionName().trim(), 70,"electionName", "electionName.maxNoChars", "No. of characters cannot be greater than 70.");
		coreValidator.rejectIfCurrentDateIsAfter(errors, electionForm.getEndDate(),"endDate", "invalid.endDate2", "End date cannot be less than today's date");
		coreValidator.rejectIfSecondDateIsBeforeFirstDate(errors, electionForm.getStartDate(), electionForm.getEndDate(),"endDate","invalid.startDate2","End date cannot be less than start date ");
	}

}
