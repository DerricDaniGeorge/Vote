package com.derric.vote.beans;

import java.util.Map;
import java.util.HashMap;

public class User {
	String votersId;
	String password;
	Map<UserDetail,Object> userDetails;
	
	public User() {
		if(userDetails==null) {
			userDetails=new HashMap<>();
		}
	}
	public void setVotersId(String votersId) {
		this.votersId=votersId;
	}
	public String getVotersId() {
		return votersId;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String getPassword() {
		return password;
	}
	public void setUserDetails(Map<UserDetail,Object> userDetails) {
		this.userDetails=userDetails;
	}
	public Object getDetail(UserDetail detail) {
		return userDetails.get(detail);
	}
	public void setDetail(UserDetail detail,Object object) {
		userDetails.put(detail, object);
	}
}
