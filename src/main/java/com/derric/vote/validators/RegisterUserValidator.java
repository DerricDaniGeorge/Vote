package com.derric.vote.validators;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Value;
import com.derric.vote.forms.RegisterUserForm;
import com.derric.vote.utils.StringUtils;

public class RegisterUserValidator implements Validator {

	@Autowired
	private StringUtils stringUtils;

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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "retypePassword", "required.retypePassword",
				"Retype password textbox cannot be empty");
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "OTP", "required.OTP", "OTP
		// must be entered");
		RegisterUserForm registerForm = (RegisterUserForm) target;
		
		if (registerForm.getVotersID() != null) {
			if (!registerForm.getVotersID().isEmpty()) {
				if (registerForm.getVotersID().length() != VOTERSID_DB_SIZE) {
					System.out.println(VOTERSID_DB_SIZE);
					errors.rejectValue("votersID", "votersID.no10characters",
							"Invalid Voter's ID. Must have 10 characters");
				}
			}
		}
		if (registerForm.getFirstName() != null) {
			if (!registerForm.getFirstName().isEmpty()) {
				if (registerForm.getFirstName().length() > FIRSTNAME_DB_SIZE) {
					errors.rejectValue("firstName", "invalid.firstName",
							"Maximum " + FIRSTNAME_DB_SIZE + " characters permitted");
				}
				if (!stringUtils.containsOnlyAlphabets(registerForm.getFirstName())) {
					errors.rejectValue("firstName", "fistName.specialCharacters", "First name must contains alphabets only");
				}
			}
		}
		
		if (registerForm.getMiddleName() != null) {
			if (!registerForm.getMiddleName().isEmpty()) {
				if (!stringUtils.containsOnlyAlphabets(registerForm.getMiddleName())) {
					errors.rejectValue("middleName", "middleName.specialCharacters",
							"Middle name must contains alphabets only");
				}
				if (registerForm.getMiddleName().length() > MIDDLENAME_DB_SIZE) {
					System.out.println(MIDDLENAME_DB_SIZE);
					errors.rejectValue("middleName", "invalid.middleName",
							"Maximum " + MIDDLENAME_DB_SIZE + " characters permitted");
				}
			}
		}
		if(registerForm.getLastName()!=null) {
			if(!registerForm.getLastName().isEmpty()) {
				if (!stringUtils.containsOnlyAlphabets(registerForm.getLastName())) {
					errors.rejectValue("lastName", "lastName.specialCharacters", "Last name must contains alphabets only");
				}
				if (registerForm.getLastName().length() > LASTNAME_DB_SIZE) {
					System.out.println(LASTNAME_DB_SIZE);
					errors.rejectValue("lastName", "invalid.lastName",
							"Maximum " + LASTNAME_DB_SIZE + " characters permitted");
				}
			}
		}
		if(registerForm.getEmail()!=null) {
			if(!registerForm.getLastName().isEmpty()) {
				
				if (registerForm.getLastName().length() > EMAIL_DB_SIZE) {
					System.out.println(EMAIL_DB_SIZE);
					errors.rejectValue("email", "invalid.email",
							"Maximum " + EMAIL_DB_SIZE + " characters permitted");
				}
			}
		}
		
		if (registerForm.getPassword() != null) {
			if (!registerForm.getPassword().isEmpty()) {
				if (registerForm.getPassword().length() < 8) {
					errors.rejectValue("password", "invalid.password", "Minimum 8 characters required");
				}
				if (registerForm.getPassword().length() > PASSWORD_DB_SIZE) {
					System.out.println(PASSWORD_DB_SIZE);
					errors.rejectValue("password", "invalid.password",
							"Maximum " + PASSWORD_DB_SIZE + " characters permitted");
				}
			}
		}
		if (registerForm.getPassword() != null) {
			if (!registerForm.getPassword().equals(registerForm.getRetypePassword())) {
				errors.rejectValue("retypePassword", "invalid.retypePassword",
						"Password & Re-type Password must match");
			}
		}
		if (registerForm.getDateOfBirth() == null) {
			errors.rejectValue("dateOfBirth", "invalid.date", "Please input date of birth");
		}
		LocalDate today = LocalDate.now();
		LocalDate years18ago = today.minusYears(18);
		int year = years18ago.getYear();
		LocalDate dateToSet = LocalDate.of(year, Month.DECEMBER, 31);
		if (registerForm.getDateOfBirth() != null) {
			LocalDate doB = registerForm.getDateOfBirth();
			System.out.println("doB-->" + doB);
			// LocalDate
			// dateOfBirth=doB.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			if (doB.isAfter(dateToSet)) {
				errors.rejectValue("dateOfBirth", "invalid.date", "Date cannot be greater than " + dateToSet);
			}
		}
		/*
		 * if (!stringUtils.containsOnlyNumbers(registerForm.getOTP())) {
		 * errors.rejectValue("OTP", "otp.otherCharacters",
		 * "OTP must contains digits only"); }
		 */
	}
}
