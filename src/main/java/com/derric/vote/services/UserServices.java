package com.derric.vote.services;

import org.springframework.beans.factory.annotation.Autowired;
import com.derric.vote.beans.User;
import com.derric.vote.beans.UserDetail;
import com.derric.vote.dao.UserDAO;
import com.derric.vote.forms.RegisterUserForm;

public class UserServices {
	@Autowired
	private UserDAO userDao;
	
	public boolean isUserAlreadyExist(User user) {
		boolean isVotersIdExists= user.getVotersId().equalsIgnoreCase(userDao.getVotersId(user)) ? true:false;
		boolean isEmailExists=(user.getDetail(UserDetail.EMAIL).toString()).equalsIgnoreCase(userDao.getEmailAddress(user))?true:false;
		return isVotersIdExists || isEmailExists;
	}
	public void addUser(User user,RegisterUserForm registerUserForm) {
		user.setPassword(registerUserForm.getPassword());
		user.setDetail(UserDetail.FIRST_NAME, registerUserForm.getFirstName().trim());
		user.setDetail(UserDetail.MIDDLE_NAME, registerUserForm.getMiddleName().trim());
		user.setDetail(UserDetail.LAST_NAME, registerUserForm.getLastName().trim());
		user.setDetail(UserDetail.GENDER, registerUserForm.getGender().trim());
		user.setDetail(UserDetail.EMAIL, registerUserForm.getEmail().trim());
		user.setDetail(UserDetail.DATE_OF_BIRTH, registerUserForm.getDateOfBirth());
		userDao.insert(user);
	}
	public void doLogin(User user) {
		
	}
}
