package com.derric.vote.constants;

public interface ICqlConstants extends IConstants {
	public static final String ADD_USER_USER_BY_EMAIL = "INSERT INTO user_by_email(voters_id,first_name,middle_name,last_name,email) VALUES(?,?,?,?,?);";
	public static final String GET_NOT_STARTED_ELECTIONS="SELECT year,election_name,activation_status,start_date,end_date FROM election_by_startdate WHERE year=? and start_date >toDate(now());";
}
