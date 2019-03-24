package com.derric.vote.validators;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.derric.vote.forms.AdminCandidateForm;

public class AdminCandidateValidator implements Validator{
	
	@Autowired
	CoreValidator coreValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return AdminCandidateForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AdminCandidateForm candidateForm=(AdminCandidateForm)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "votersId", "required.votersID", "Voter's ID required");
		coreValidator.rejectNotStringContainsOnlyAlphabetsAndNumbers(errors, candidateForm.getVotersId().trim(), "votersId", "votersID.othercharacters", "VotersID must contains only letters and digits");
		coreValidator.rejectNotStringExactCharacters(errors, candidateForm.getVotersId().trim(), 10, "votersId", "votersID.noexactcharacters", "Invalid Voter's ID. Must have "+10+" characters");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "required.firstName", "First Name required");
		coreValidator.rejectNotStringMaxCharacters(errors, candidateForm.getFirstName().trim(), 70, "firstName", "invalid.firstName", "Maximum " + 70 + " characters permitted");
		coreValidator.rejectNotStringContainsOnlyAlphabetsIgnoreSpace(errors, candidateForm.getFirstName().trim(),"firstName", "firstName.specialCharacters", "First name must contains alphabets only");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "required.lastName", "Last Name required");
		coreValidator.rejectNotStringContainsOnlyAlphabets(errors, candidateForm.getLastName().trim(),"lastName", "lastName.specialCharacters", "Last name must contains alphabets only");
		coreValidator.rejectNotStringMaxCharacters(errors, candidateForm.getLastName().trim(), 70, "lastName", "invalid.lastName", "Maximum " + 70 + " characters permitted");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "party", "required.party", "Party Name required");
		coreValidator.rejectIfValueNotInRange(errors, 18, 150, candidateForm.getAge(), "age", "invalid.age", "Age must be greater than 17 and less than 151");
		
	}
}
