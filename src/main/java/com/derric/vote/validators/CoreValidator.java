package com.derric.vote.validators;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import com.derric.vote.utils.StringUtils;

public class CoreValidator {
	
	@Autowired
	StringUtils stringUtils;

	public void rejectNotStringMinCharacters(Errors errors, String text,int minimumCharacters,String fieldName,String errorCode,String defaultMessage) {
		if (text != null && !text.isEmpty() && text.length() < minimumCharacters) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	public void rejectNotStringExactCharacters(Errors errors, String text,int exactNoOfCharacters,String fieldName,String errorCode,String defaultMessage) {
		if (text != null && !text.isEmpty() && text.length() != exactNoOfCharacters) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);		
		}
	}
	public void rejectNotStringMaxCharacters(Errors errors, String text,int maximumCharacters,String fieldName,String errorCode,String defaultMessage) {
		if (text != null && !text.isEmpty() && text.length() > maximumCharacters) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	public void rejectNotStringContainsOnlyAlphabets(Errors errors, String text,String fieldName,String errorCode,String defaultMessage) {
		if (text != null && !text.isEmpty() && !stringUtils.containsOnlyAlphabets(text)) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	public void rejectNotStringContainsOnlyAlphabetsIgnoreSpace(Errors errors, String text,String fieldName,String errorCode,String defaultMessage) {
		if (text != null && !text.isEmpty() && !stringUtils.containsOnlyAlphabetsIgnoreSpace(text)) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	public void rejectNotStringContainsOnlyAlphabetsAndNumbers(Errors errors, String text,String fieldName,String errorCode,String defaultMessage) {
		if (text != null && !text.isEmpty() && !stringUtils.containsOnlyLettersOrDigits(text)) {
					errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	
	public void rejectNotTwoStringsAreExactlyEqual(Errors errors, String text1,String text2,String fieldName,String errorCode,String defaultMessage) {
		if (text1 != null && text2!=null && !text1.equalsIgnoreCase(text2)) {
				errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	public void rejectIfCurrentDateIsAfter(Errors errors, LocalDate date,String fieldName,String errorCode,String defaultMessage) {
		LocalDate today=LocalDate.now();
		if(date!=null && today.isAfter(date)) {
				errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	public void rejectIfCurrentDateIsBefore(Errors errors, LocalDate date,String fieldName,String errorCode,String defaultMessage) {
		LocalDate today=LocalDate.now();
		if(date!=null && today.isBefore(date)) {
				errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	public void rejectIfSecondDateIsBeforeFirstDate(Errors errors, LocalDate firstDate,LocalDate secondDate,String fieldName,String errorCode,String defaultMessage) {
		if(firstDate!=null && secondDate!=null && secondDate.isBefore(firstDate)) {
				errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	public void rejectIfValueNotInRange(Errors errors, int lowerLimit,int upperLimit,int value,String fieldName,String errorCode,String defaultMessage) {
		if(!(value>=lowerLimit && value<=upperLimit)) {
			errors.rejectValue(fieldName, errorCode,defaultMessage);
		}
	}
	public boolean rejectIfNoUploadFileFound(Errors errors, Part part,String fieldName,String errorCode,String defaultMessage) {
		boolean isRejected=false;
		if(part.getSubmittedFileName().isEmpty()) {
			errors.rejectValue(fieldName, errorCode,defaultMessage);
			isRejected=true;
		}
		return isRejected;
	}
	public boolean rejectIfNotExpectedFileType(Errors errors,Part part,List<String> fileExtensions,String fieldName,String errorCode,String defaultMessage) {
		boolean isRejected=false;
		String fileName=part.getSubmittedFileName();
		System.out.println("fileName==>"+fileName);
		String extension=fileName.substring(fileName.indexOf('.'), fileName.length());
		System.out.println("extension==>"+extension);
		if(!fileExtensions.contains(extension)) {
			errors.rejectValue(fieldName, errorCode,defaultMessage);
			isRejected=true;
		}
		return isRejected;
	}
	public boolean rejectIfFileSizeIsMore(Errors errors,Part part,long sizeInBytes,String fieldName,String errorCode,String defaultMessage) {
		boolean isRejected=false;
		if(part.getSize()>sizeInBytes) {
			errors.rejectValue(fieldName, errorCode,defaultMessage);
			isRejected=true;
		}
		return isRejected;
	}
}
