package com.derric.vote.validators;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import com.derric.vote.utils.StringUtils;

public class CoreValidator {
	
	@Autowired
	StringUtils stringUtils;

	public void rejectNotStringMinCharacters(Errors errors, String text,int minimumCharacters,String fieldName,String errorCode,String defaultMessage) {
		if (text != null) {
			if (!text.isEmpty()) {
				if (text.length() < minimumCharacters) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
				}
			}
		}
	}
	public void rejectNotStringExactCharacters(Errors errors, String text,int exactNoOfCharacters,String fieldName,String errorCode,String defaultMessage) {
		if (text != null) {
			if (!text.isEmpty()) {
				if (text.length() != exactNoOfCharacters) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
				}
			}
		}
	}
	public void rejectNotStringMaxCharacters(Errors errors, String text,int maximumCharacters,String fieldName,String errorCode,String defaultMessage) {
		if (text != null) {
			if (!text.isEmpty()) {
				if (text.length() > maximumCharacters) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
				}
			}
		}
	}
	public void rejectNotStringContainsOnlyAlphabets(Errors errors, String text,String fieldName,String errorCode,String defaultMessage) {
		if (text != null) {
			if (!text.isEmpty()) {
				if (!stringUtils.containsOnlyAlphabets(text)) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
				}
			}
		}
	}
	public void rejectNotTwoStringsAreExactlyEqual(Errors errors, String text1,String text2,String fieldName,String errorCode,String defaultMessage) {
		if (text1 != null && text2!=null) {
				if (!text1.equalsIgnoreCase(text2)) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
				}	
		}
	}
	public void rejectNotIfCurrentDateIsAfter(Errors errors, LocalDate date,String fieldName,String errorCode,String defaultMessage) {
		LocalDate today=LocalDate.now();
		if(date!=null) {
			if(today.isAfter(date)) {
				errors.rejectValue(fieldName, errorCode,defaultMessage);
			}
		}
	}
	public void rejectNotIfCurrentDateIsBefore(Errors errors, LocalDate date,String fieldName,String errorCode,String defaultMessage) {
		LocalDate today=LocalDate.now();
		if(date!=null) {
			if(today.isBefore(date)) {
				errors.rejectValue(fieldName, errorCode,defaultMessage);
			}
		}
	}
	
}
