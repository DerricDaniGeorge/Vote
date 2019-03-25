package com.derric.vote.constants;

public class CqlConstants {
	private CqlConstants() {}
	public static final String ADD_USER_USER_BY_EMAIL = "INSERT INTO user_by_email(voters_id,first_name,middle_name,last_name,email) VALUES(?,?,?,?,?);";
	public static final String GET_NOT_STARTED_ELECTIONS = "SELECT election_id,year,election_name,activation_status,start_date,end_date FROM election_by_startdate WHERE year>=? AND start_date >toDate(now()) allow filtering;";
	public static final String ADD_ELECTION_BY_STARTDATE = "INSERT INTO election_by_startdate(year,start_date,election_id,activation_status,creation_time,election_name,end_date,last_modified_time,last_modifier_key) VALUES(?,?,?,?,?,?,?,?,?);";
	public static final String ADD_ELECTION_BY_ENDDATE = "INSERT INTO election_by_enddate(year,start_date,election_id,activation_status,creation_time,election_name,end_date,last_modified_time,last_modifier_key) VALUES(?,?,?,?,?,?,?,?,?);";
	public static final String GET_ELECTION_CREATION_TIME="SELECT creation_time FROM election_by_startdate WHERE year=? AND start_date=? AND election_id=?";
	public static final String DELETE_ELECTION_ELECTION_BY_STARTDATE="DELETE FROM election_by_startdate WHERE year=? AND start_date=? AND election_id=?;";
	public static final String DELETE_ELECTION_ELECTION_BY_ENDDATE="DELETE FROM election_by_enddate WHERE year=? AND end_date=? AND election_id=?;";
	public static final String ADD_CANDIDATE="INSERT INTO candidate(voters_id,first_name,last_name,party,age,profile_photo,election_symbol_img,profile_photo_length,symbol_img_length,creation_time,last_modified_time,last_modifier_key,state,constituency,mapping_id,election_name,election_id,election_start_date,election_end_date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	public static final String GET_ALL_CANDIDATE_DETAILS="SELECT voters_id,first_name,last_name,party,age,profile_photo,election_symbol_img FROM candidate";
	public static final String GET_ALL_STATES="SELECT state FROM state";
	public static final String ADD_STATE="INSERT INTO state(state,creation_time,last_modifier_key,last_modified_time) VALUES(?,?,?,?);";
	public static final String DELETE_STATE="DELETE FROM state WHERE state=?;";
	public static final String GET_ALL_LOKSABHA_CONSTITUECY_NAMES="SELECT constituency FROM constituencies_loksabha";
	public static final String ADD_LOKSAHBA_CONSTITUENCY="INSERT INTO constituencies_loksabha(constituency,creation_time,last_modifier_key,last_modified_time) VALUES(?,?,?,?);";
	public static final String DELETE_LOKSABHA_CONSTITUENCY="DELETE FROM constituencies_loksabha WHERE constituency=?;";
	public static final String MAP_LOKSABHA_CONSTITUENCY="INSERT INTO state_loksabha_constituencies(creation_time,last_modified_time,last_modifier_key,state,loksabha_constituencies) VALUES (?,?,?,?,?)";
	public static final String GET_STATE_LOK_SABHA_MAPPING="SELECT state FROM state_loksabha_constituencies WHERE state=?;";
	public static final String UPDATE_STATE_LOK_SABHA_MAPPING="UPDATE state_loksabha_constituencies SET last_modified_time=?,last_modifier_key=?,loksabha_constituencies=? WHERE state=?";
	public static final String GET_MAPPED_CONSTITUENCIES_STATE="SELECT loksabha_constituencies FROM state_loksabha_constituencies WHERE state=?;";
	
}
