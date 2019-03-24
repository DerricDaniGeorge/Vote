package com.derric.vote.validators;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.derric.vote.forms.RegisterUserForm;

public class RegisterUserValidator implements Validator {


	@Autowired
	private CoreValidator coreValidator;

	private @Value("${votersID.dbcolumn.size:10}") int VOTERSID_DB_SIZE;
	private @Value("${firstName.dbcolumn.size:50}") int FIRSTNAME_DB_SIZE;
	private @Value("${middleName.dbcolumn.size:50}") int MIDDLENAME_DB_SIZE;
	private @Value("${lastName.dbcolumn.size:50}") int LASTNAME_DB_SIZE;
	private @Value("${email.dbcolumn.size:50}") int EMAIL_DB_SIZE;
	private @Value("${password.dbcolumn.size:50}") int PASSWORD_DB_SIZE;

	public boolean supports(Class<?> clazz) {
		return RegisterUserForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "votersID", "required.votersID", "Voter's ID required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "required.firstName", "First Name required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "required.lastName", "Last Name required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required.email", "Email required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required.password", "Password required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retypePassword", "required.retypePassword","Retype password textbox cannot be empty");
		RegisterUserForm registerForm = (RegisterUserForm) target;
		coreValidator.rejectNotStringContainsOnlyAlphabetsAndNumbers(errors, registerForm.getVotersID().trim(), "votersID", "votersID.othercharacters", "VotersID must contains only letters and digits");
		coreValidator.rejectNotStringExactCharacters(errors, registerForm.getVotersID().trim(), VOTERSID_DB_SIZE, "votersID", "votersID.noexactcharacters", "Invalid Voter's ID. Must have "+VOTERSID_DB_SIZE+" characters");
		coreValidator.rejectNotStringMaxCharacters(errors, registerForm.getFirstName().trim(), FIRSTNAME_DB_SIZE, "firstName", "invalid.firstName", "Maximum " + FIRSTNAME_DB_SIZE + " characters permitted");
		coreValidator.rejectNotStringContainsOnlyAlphabets(errors, registerForm.getFirstName().trim(),"firstName", "firstName.specialCharacters", "First name must contains alphabets only");
		coreValidator.rejectNotStringContainsOnlyAlphabets(errors, registerForm.getMiddleName().trim(),"middleName", "middleName.specialCharacters", "Middle name must contains alphabets only");
		coreValidator.rejectNotStringMaxCharacters(errors, registerForm.getMiddleName().trim(), MIDDLENAME_DB_SIZE, "middleName", "invalid.middleName", "Maximum " + MIDDLENAME_DB_SIZE + " characters permitted");
		coreValidator.rejectNotStringContainsOnlyAlphabets(errors, registerForm.getLastName().trim(),"lastName", "lastName.specialCharacters", "Last name must contains alphabets only");
		coreValidator.rejectNotStringMaxCharacters(errors, registerForm.getLastName().trim(), LASTNAME_DB_SIZE, "lastName", "invalid.lastName", "Maximum " + LASTNAME_DB_SIZE + " characters permitted");
		coreValidator.rejectNotStringMinCharacters(errors, registerForm.getPassword().trim(), 8, "password", "invalid.password", "Minimum 8 characters required");
		coreValidator.rejectNotStringMaxCharacters(errors, registerForm.getPassword().trim(), PASSWORD_DB_SIZE, "password", "invalid.password", "Maximum " + PASSWORD_DB_SIZE + " characters permitted");
		coreValidator.rejectNotStringMaxCharacters(errors, registerForm.getEmail().trim(), EMAIL_DB_SIZE, "email", "invalid.email", "Maximum " + EMAIL_DB_SIZE + " characters permitted");
		coreValidator.rejectNotTwoStringsAreExactlyEqual(errors, registerForm.getPassword(), registerForm.getRetypePassword(), "retypePassword", "invalid.retypePassword", "Password & Re-type Password must match");

		if (registerForm.getDateOfBirth() == null) {
			errors.rejectValue("dateOfBirth", "invalid.date", "Please input date of birth");
		}
		LocalDate today = LocalDate.now();
		LocalDate years18ago = today.minusYears(18);
		int year = years18ago.getYear();
		LocalDate dateToSet = LocalDate.of(year, Month.DECEMBER, 31);
		if (registerForm.getDateOfBirth() != null) {
			LocalDate doB = registerForm.getDateOfBirth();
//			System.out.println("doB-->" + doB);
			// LocalDate dateOfBirth=doB.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (doB.isAfter(dateToSet)) {
				errors.rejectValue("dateOfBirth", "invalid.date", "Date cannot be greater than " + dateToSet);
			}
		}
		
	}
}
