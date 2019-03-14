package com.derric.vote.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.derric.vote.beans.User;
import com.derric.vote.beans.UserDetail;
import com.derric.vote.dao.IUserDBService;
import com.derric.vote.forms.RegisterUserForm;

public class UserServices {
	@Autowired
	private IUserDBService userDBService;

	public boolean isAccountAlreadyExist(User user) {
		String votersId=userDBService.getVotersId(user);
		boolean isVotersIdExists =(votersId==null)?false:(user.getVotersId()).equalsIgnoreCase(votersId)?true:false;
		String email=userDBService.getEmailAddress(user);
		boolean isEmailExists=(email==null)?false:(user.getDetail(UserDetail.EMAIL).toString()).equalsIgnoreCase(email)?true:false;
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
		userDBService.insertUser(user);
	}

	public String doLogin(User user) {
		boolean isValidUser = false;
		String role=null;
		String password = userDBService.getUserPassword(user);
		if (password != null) {
			isValidUser = password.equals(user.getPassword()) ? true : false;
		}
		if(isValidUser) {
			role=userDBService.getUserRole(user);
		}
		return role;
	}
}
