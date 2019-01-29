package com.derric.vote.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.derric.vote.beans.User;
import com.derric.vote.beans.UserDetail;
import com.derric.vote.dao.IUserDBService;
import com.derric.vote.forms.RegisterUserForm;

public class UserServices {
	@Autowired
	private IUserDBService userDBService;

	public boolean isUserAlreadyExist(User user) {
		boolean isVotersIdExists = user.getVotersId().equalsIgnoreCase(userDBService.getVotersId(user)) ? true : false;
		boolean isEmailExists = (user.getDetail(UserDetail.EMAIL).toString())
				.equalsIgnoreCase(userDBService.getEmailAddress(user)) ? true : false;
		return isVotersIdExists || isEmailExists;
	}

	public void addUser(User user, RegisterUserForm registerUserForm) {
		user.setPassword(registerUserForm.getPassword());
		user.setDetail(UserDetail.FIRST_NAME, registerUserForm.getFirstName().trim());
		user.setDetail(UserDetail.MIDDLE_NAME, registerUserForm.getMiddleName().trim());
		user.setDetail(UserDetail.LAST_NAME, registerUserForm.getLastName().trim());
		user.setDetail(UserDetail.GENDER, registerUserForm.getGender().trim());
		user.setDetail(UserDetail.EMAIL, registerUserForm.getEmail().trim());
		user.setDetail(UserDetail.DATE_OF_BIRTH, registerUserForm.getDateOfBirth());
		userDBService.insert(user);
	}

	public boolean doLogin(User user) {
		boolean isValidUser = false;
		String password = userDBService.getUserPassword(user);
		if (password != null) {
			isValidUser = password.equals(user.getPassword()) ? true : false;
		}
		return isValidUser;
	}
}
