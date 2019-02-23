package com.derric.vote.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.derric.vote.beans.ElectionDetail;
import com.derric.vote.forms.AdminElectionForm;
import com.derric.vote.forms.LoginForm;

public class AdminElectionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return AdminElectionForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "election_name", "required.electionName", "Election Name required");
		AdminElectionForm electionForm=(AdminElectionForm) target;
		if (electionForm.getElection().getDetail(ElectionDetail.START_DATE) == null) {
			errors.rejectValue("start_date", "invalid.date", "Please input start date");
		}
		if (electionForm.getElection().getDetail(ElectionDetail.END_DATE) == null) {
			errors.rejectValue("end_date", "invalid.date", "Please input end date");
		}
	}

}
