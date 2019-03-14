package com.derric.vote.constants;

public class QueryConstants {
	
	private QueryConstants() {}
	public static final String ADD_USER_APP_USER = "INSERT INTO app_user(voters_id,first_name,middle_name,last_name,gender,"
			+ "date_of_birth,email,password,role,account_status,creation_time,last_modified_time,last_modifier_key) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String GET_VOTERSID_BY_VOTERSID = "SELECT voters_id FROM app_user WHERE voters_id=?";
	public static final String GET_EMAILID_BY_EMAILID = "SELECT email FROM user_by_email WHERE email=?";
	public static final String GET_PASSWORD_BY_VOTERSID = "SELECT password FROM app_user WHERE voters_id=?";
	public static final String GET_ROLE_BY_VOTERSID = "SELECT role FROM app_user WHERE voters_id=?";
	public static final String GET_ALL_ELECTION_NAMES="SELECT election_name from election_by_startdate";

}
