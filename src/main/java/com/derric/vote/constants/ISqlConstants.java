package com.derric.vote.constants;

public interface ISqlConstants {
	public static final String ADD_USER_APP_USER = "INSERT INTO app_user(voters_id,first_name,middle_name,last_name,gender,"
			+ "date_of_birth,email,password,role) VALUES(?,?,?,?,?,?,?,?,?)";
	public static final String ADD_USER_USER_BY_EMAIL = "INSERT INTO user_by_email(voters_id,first_name,middle_name,last_name,email) VALUES(?,?,?,?,?)";
	public static final String GET_VOTERSID_BY_VOTERSID = "SELECT voters_id FROM app_user WHERE voters_id=?";
	public static final String GET_EMAILID_BY_EMAILID = "SELECT email FROM user_by_email WHERE email=?";
	public static final String GET_PASSWORD_BY_VOTERSID = "SELECT password FROM app_user WHERE voters_id=?";
}
